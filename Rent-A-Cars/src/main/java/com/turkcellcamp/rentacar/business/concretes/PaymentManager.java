package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CreditCardService;
import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetPaymentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListPaymentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.PaymentDao;
import com.turkcellcamp.rentacar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {
	
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private InvoiceService invoiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private PosService posService;
	private CreditCardService creditCardService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,InvoiceService invoiceService, OrderedAdditionalServiceService orderedAdditionalServiceService, PosService posService, CreditCardService creditCardService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.posService = posService;
		this.creditCardService = creditCardService;
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAll() {

		var result = this.paymentDao.findAll();

		List<ListPaymentDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListPaymentDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(boolean rememberMe,CreatePaymentRequest createPaymentRequest) {
	
		checkIfInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkPaymentIfInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkIfOrderedAdditionalServiceExists(createPaymentRequest.getOrderedAdditionalServiceId());
		
		this.posService.payments(createPaymentRequest.getCreateCreditCard().getCardOwnerName(), createPaymentRequest.getCreateCreditCard().getCardNumber(), createPaymentRequest.getCreateCreditCard().getCardCvvNumber());
		
		saveCreditCard(rememberMe, createPaymentRequest.getCreateCreditCard());
		
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		payment.setPaymentId(0);

		this.paymentDao.save(payment);

		return new SuccessResult(BusinessMessages.PAYMENTADDED);
	}
	
	@Override
	public Result delete(int id) {
		
		checkIfPaymentExists(id);

		this.paymentDao.deleteById(id);

		return new SuccessResult(BusinessMessages.PAYMENTDELETED);
	}

	@Override
	public DataResult<GetPaymentByIdDto> getById(int paymentId) {

		Payment result = checkIfPaymentExists(paymentId);

		GetPaymentByIdDto response = this.modelMapperService.forDto().map(result, GetPaymentByIdDto.class);

		return new SuccessDataResult<GetPaymentByIdDto>(response, BusinessMessages.SUCCESS);
	}

	private Payment checkIfPaymentExists(int paymentId) {

		Payment payment = this.paymentDao.getByPaymentId(paymentId);

		if (payment == null) {
			throw new BusinessException(BusinessMessages.PAYMENTNOTFOUND);
		}
		return payment;
	}
	private void checkIfInvoiceExists(int invoiceId) {
		
	    if(this.invoiceService.getById(invoiceId)==null) {
	    	throw new BusinessException(BusinessMessages.INVOICENOTFOUND);
	    }
	}
	private void checkIfOrderedAdditionalServiceExists(int orderedAdditionalServiceId) {
		
	    if(this.orderedAdditionalServiceService.getById(orderedAdditionalServiceId)==null) {
	    	throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICENOTFOUND);
	    }
	}
	
	private void checkPaymentIfInvoiceExists(int id) {
		
		if(this.paymentDao.getByInvoice_invoiceId(id)!=null) {
			throw new BusinessException(BusinessMessages.INVOICEANPAYMENTED);
		}
	}

	private void saveCreditCard(boolean rememberMe, CreateCreditCardRequest creditCard) {
		
		if(rememberMe) {
			creditCardService.add(creditCard);
		}
	}

}
