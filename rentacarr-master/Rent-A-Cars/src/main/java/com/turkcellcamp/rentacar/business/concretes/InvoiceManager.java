package com.turkcellcamp.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetColorByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListInvoiceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.InvoiceDao;
import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
	
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		GetCustomerByIdDto customer = this.customerService.getById(createInvoiceRequest.getCustomerId()).getData();
		
		Customer c = this.modelMapperService.forDto().map(customer, Customer.class);
		
		invoice.setCustomer(c);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		this.invoiceDao.deleteById(id);
		
		return new SuccessResult("Invoice.Deleted");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response,"Success");
	}

	@Override
	public Result update(int id, UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(id);
		updateOperation(invoice,updateInvoiceRequest);
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Updated");
	}


	@Override
	public DataResult<GetInvoiceByIdDto> getByInvoiceId(int invoiceId) throws BusinessException {
		
		Invoice result = this.invoiceDao.getByInvoiceId(invoiceId);
		if (result != null) {
			GetInvoiceByIdDto response = this.modelMapperService.forDto().map(result, GetInvoiceByIdDto.class);
			
			return new SuccessDataResult<GetInvoiceByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetInvoiceByIdDto>("Cannot find a color with this Id.");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		List<Invoice> result = this.invoiceDao.getByCustomer_customerId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	private void updateOperation(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());;
		
	}
	
}
