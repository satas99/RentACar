package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetPaymentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListPaymentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.core.PosServices.HalkBankService;
import com.turkcellcamp.rentacar.core.PosServices.IsBankService;
import com.turkcellcamp.rentacar.core.PosServices.PosService;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.PaymentDao;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;
import com.turkcellcamp.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private InvoiceService invoiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private IsBankService isBankService;
	private HalkBankService halkBankService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,InvoiceService invoiceService, OrderedAdditionalServiceService orderedAdditionalServiceService, IsBankService isBankService, HalkBankService halkBankService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.isBankService = isBankService;
		this.halkBankService = halkBankService;
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAll() {

		var result = this.paymentDao.findAll();

		List<ListPaymentDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListPaymentDto>>(response, "Success");
	}

	@Override
	public Result isBankAdd(CreatePaymentRequest createPaymentRequest) {
		
		checkIfInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkPaymentInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkIfOrderedAdditionalServiceExists(createPaymentRequest.getOrderedAdditionalServiceId());
		
		checkPaymentOrderedAdditionalServiceExists(createPaymentRequest.getOrderedAdditionalServiceId());
		
		this.isBankService.payments(createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		this.paymentDao.save(payment);

		return new SuccessResult("Payment.Added ");
	}
	@Override
	public Result halkBankAdd(CreatePaymentRequest createPaymentRequest) {
		
		
		checkIfInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkPaymentInvoiceExists(createPaymentRequest.getInvoiceId());
		
		checkIfOrderedAdditionalServiceExists(createPaymentRequest.getOrderedAdditionalServiceId());
		
		checkPaymentOrderedAdditionalServiceExists(createPaymentRequest.getOrderedAdditionalServiceId());
		
		this.halkBankService.payments( createPaymentRequest.getCardNumber(), createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardCvvNumber());

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		this.paymentDao.save(payment);

		return new SuccessResult("Payment.Added ");
	}


	@Override
	public DataResult<GetPaymentByIdDto> getById(int paymentId) {

		Payment result = checkIfPaymentExists(paymentId);

		GetPaymentByIdDto response = this.modelMapperService.forDto().map(result, GetPaymentByIdDto.class);

		return new SuccessDataResult<GetPaymentByIdDto>(response, "Success");
	}

	@Override
	public Result delete(int id) {
		
		checkIfPaymentExists(id);

		this.paymentDao.deleteById(id);

		return new SuccessResult("Payment.Deleted");
	}

	private Payment checkIfPaymentExists(int paymentId) {

		Payment payment = this.paymentDao.getByPaymentId(paymentId);

		if (payment == null) {
			throw new BusinessException("The payment with this id does not exist..");
		}
		return payment;
	}
	private void checkIfInvoiceExists(int invoiceId) {
		
	    if(this.invoiceService.getById(invoiceId)==null) {
	    	throw new BusinessException("Cannot find an invoice with this Id.");
	    }
	}
	private void checkIfOrderedAdditionalServiceExists(int orderedAdditionalServiceId) {
		
	    if(this.orderedAdditionalServiceService.getById(orderedAdditionalServiceId)==null) {
	    	throw new BusinessException("Cannot find an ordered additional service with this Id.");
	    }
	}
	
	private void checkPaymentInvoiceExists(int id) {
		
		if(this.paymentDao.getByInvoice_invoiceId(id)!=null) {
			throw new BusinessException("Can find an invoice with this payment id");
		}
	}
	
	private void checkPaymentOrderedAdditionalServiceExists(int id) {
		
		if(this.paymentDao.getByOrderedAdditionalService_orderedAdditionalServiceId(id)!=null) {
			throw new BusinessException("Can find an ordered additional service with this payment id");
		}
	}
	

}
