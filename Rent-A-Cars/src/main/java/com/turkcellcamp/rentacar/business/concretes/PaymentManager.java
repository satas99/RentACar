package com.turkcellcamp.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.CreditCardService;
import com.turkcellcamp.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetPaymentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListPaymentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateLateDeliveriesPaymentRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.PaymentDao;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;
import com.turkcellcamp.rentacar.entities.concretes.Payment;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
import net.bytebuddy.utility.RandomString;

@Service
public class PaymentManager implements PaymentService {
	
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private InvoiceService invoiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private PosService posService;
	private CreditCardService creditCardService;
	private RentalCarService rentalCarService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, InvoiceService invoiceService,OrderedAdditionalServiceService orderedAdditionalServiceService, PosService posService,CreditCardService creditCardService, RentalCarService rentalCarService,IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.posService = posService;
		this.creditCardService = creditCardService;
		this.rentalCarService = rentalCarService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}
	@Override
	public DataResult<List<ListPaymentDto>> getAll() {

		var result = this.paymentDao.findAll();

		List<ListPaymentDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListPaymentDto>>(response, BusinessMessages.SUCCESS);
	}
	@Transactional
	@Override
	public Result add(boolean rememberMe,CreatePaymentRequest createPaymentRequest) {
		
		this.posService.doPayment(createPaymentRequest.getCreateCreditCard().getCardOwnerName(), createPaymentRequest.getCreateCreditCard().getCardNumber(), createPaymentRequest.getCreateCreditCard().getCardCvvNumber());
		
		runPaymentProcess(rememberMe , createPaymentRequest);

		return new SuccessResult(BusinessMessages.PAYMENTADDED);
	}
	
	@Transactional
	@Override
	public void addForLateDelivery(CreateLateDeliveriesPaymentRequest createLateDeliveriesPaymentRequest) {
		
		this.posService.doPayment(createLateDeliveriesPaymentRequest.getCreateCreditCard().getCardOwnerName(), createLateDeliveriesPaymentRequest.getCreateCreditCard().getCardNumber(), createLateDeliveriesPaymentRequest.getCreateCreditCard().getCardCvvNumber());
		runLateDeliveriesPaymentProcess(createLateDeliveriesPaymentRequest);

		
	}
	
	@Override
	public Result delete(int id) {
		
		checkIfPaymentExists(id);

		this.paymentDao.deleteById(id);

		return new SuccessResult(BusinessMessages.PAYMENTDELETED);
	}

	@Override
	public DataResult<GetPaymentByIdDto> getById(int paymentId) {

		Payment result = checkIfPaymentExists(paymentId); //getPaymentOrThrowExceptionIfNotExists

		GetPaymentByIdDto response = this.modelMapperService.forDto().map(result, GetPaymentByIdDto.class);

		return new SuccessDataResult<GetPaymentByIdDto>(response, BusinessMessages.SUCCESS);
	}
	
	@Transactional
	private void runPaymentProcess(boolean rememberMe, CreatePaymentRequest createPaymentRequest) {
		
		RentalCar rentalCar = saveRentalCarByCustomerType(createPaymentRequest);
	
		saveOrderedAdditionalService(createPaymentRequest.getAdditionalServiceIds(), rentalCar.getRentalCarId());
		
		saveCreditCard(rememberMe, createPaymentRequest.getCreateCreditCard());
		
		Invoice invoice = saveInvoiceByCustomerType(rentalCar);
		
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		savePayment(payment, rentalCar, invoice);
		

	}
	
	@Transactional
	private void runLateDeliveriesPaymentProcess(CreateLateDeliveriesPaymentRequest createLateDeliveriesPaymentRequest) {
		
		RentalCar rentalCar = getByRentalCar(createLateDeliveriesPaymentRequest);
		
		Invoice invoice = saveInvoiceByCustomerType(rentalCar);
		
		Payment payment = this.modelMapperService.forRequest().map(createLateDeliveriesPaymentRequest, Payment.class);
		savePayment(payment, rentalCar, invoice);
		

	}

	private RentalCar getByRentalCar(CreateLateDeliveriesPaymentRequest createLateDeliveriesPaymentRequest) {
		
		GetRentalCarByIdDto getRentalCarByIdDto =this.rentalCarService.getById(createLateDeliveriesPaymentRequest.getRentalCarId()).getData();
		
		return this.modelMapperService.forRequest().map(getRentalCarByIdDto, RentalCar.class);
		
	}
	private RentalCar saveRentalCarByCustomerType(CreatePaymentRequest createPaymentRequest) {
		
		if(this.individualCustomerService.checkIndividualCustomerIfForRental(createPaymentRequest.getRentalCar().getCustomerId())) {
			return this.rentalCarService.addForIndividualCustomer(createPaymentRequest.getRentalCar());
		}
		else if(this.corporateCustomerService.checkCorporateCustomerIfForRental(createPaymentRequest.getRentalCar().getCustomerId())) {
			return this.rentalCarService.addForCorporateCustomer(createPaymentRequest.getRentalCar());
		}
		throw new BusinessException(BusinessMessages.CUSTOMERNOTFOUND);		
	}
	
	private void saveOrderedAdditionalService(List<Integer> additionalServiceIds, int rentalCarId) {
		
		this.orderedAdditionalServiceService.add(additionalServiceIds,rentalCarId);
	}
	
	private void saveCreditCard(boolean rememberMe, CreateCreditCardRequest creditCard) {
		
		if(rememberMe) {
			creditCardService.add(creditCard);
		}
	}
	
	private Invoice saveInvoiceByCustomerType(RentalCar rentalCar) {
		
		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
		createInvoiceRequest.setInvoiceNo(RandomString.make(10));
		createInvoiceRequest.setCustomerId(rentalCar.getCustomer().getCustomerId());
		createInvoiceRequest.setRentalCarId(rentalCar.getRentalCarId());
		
		if(this.individualCustomerService.checkIndividualCustomerIfForRental(rentalCar.getCustomer().getCustomerId())) {
			return this.invoiceService.addForIndividualCustomer(createInvoiceRequest);
		}
		else if(this.corporateCustomerService.checkCorporateCustomerIfForRental(rentalCar.getCustomer().getCustomerId())) {
			return this.invoiceService.addForCorporateCustomer(createInvoiceRequest);
		}
		throw new BusinessException(BusinessMessages.CUSTOMERNOTFOUND);
	
	}

	private void savePayment(Payment payment, RentalCar rentalCar, Invoice invoice) {
		
		payment.setCustomerId(rentalCar.getCustomer().getCustomerId());
		payment.setInvoiceId(invoice.getInvoiceId());
		payment.setPaymentTotal(invoice.getRentTotalPrice());
		payment.setPaymentDate(LocalDate.now());
		payment.setRentalCarId(rentalCar.getRentalCarId());
		payment.setPaymentId(0);

		this.paymentDao.save(payment);
	}

	private Payment checkIfPaymentExists(int paymentId) {

		Payment payment = this.paymentDao.getByPaymentId(paymentId);

		if (payment == null) {
			throw new BusinessException(BusinessMessages.PAYMENTNOTFOUND);
		}
		return payment;
	}


}
