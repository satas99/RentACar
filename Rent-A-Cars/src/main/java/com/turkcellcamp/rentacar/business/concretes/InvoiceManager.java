package com.turkcellcamp.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListInvoiceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.InvoiceDao;
import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentalCarService rentalCarService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService,RentalCarService rentalCarService, OrderedAdditionalServiceService orderedAdditionalServiceService, AdditionalServiceService additionalServiceService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentalCarService = rentalCarService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		invoice.setInvoiceId(0);
		
		checkIfInvoiceNoExists(invoice.getInvoiceNo());
		
		idCorrectionForAdd(invoice, createInvoiceRequest);

		invoiceTableSetColumns(invoice, createInvoiceRequest);
		
		checkIfCustomerExists(invoice.getCustomer().getCustomerId());
		
		checkIfRentalCarExists(invoice.getRentalCar().getRentalCarId());
		
		checkInvoiceIfRentalCarExists(invoice.getRentalCar().getRentalCarId());
		
		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfInvoiceExists(id);
		
		this.invoiceDao.deleteById(id);

		return new SuccessResult("Invoice.Deleted");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {

		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());

		response = idCorrectionGetAll(result, response);

		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public Result update(int id, UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExists(id);

		Invoice invoice = this.invoiceDao.getByInvoiceId(id);
		updateOperation(invoice, updateInvoiceRequest);
		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice.Updated");
	}

	@Override
	public DataResult<GetInvoiceByIdDto> getById(int invoiceId) throws BusinessException {

		Invoice result = checkIfInvoiceExists(invoiceId);
		
		GetInvoiceByIdDto response = this.modelMapperService.forDto().map(result, GetInvoiceByIdDto.class);
		
		idCorrectionForGetById(result, response);

		return new SuccessDataResult<GetInvoiceByIdDto>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {

		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());
		
		idCorrectionGetAll(result, response);
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {

		List<Invoice> result = this.invoiceDao.getByCustomer_customerId(id);
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());

		idCorrectionGetAll(result, response);

		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	private void updateOperation(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());

	}

	private void idCorrectionForAdd(Invoice invoice, CreateInvoiceRequest createInvoiceRequest) {

		GetCustomerByIdDto getCustomerByIdDto = this.customerService.getById(invoice.getCustomer().getCustomerId()).getData();
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);

		GetRentalCarByIdDto getRentalCarByIdDto = this.rentalCarService.getById(invoice.getRentalCar().getRentalCarId()).getData();
		RentalCar rentalCar = this.modelMapperService.forDto().map(getRentalCarByIdDto, RentalCar.class);

		invoice.setCustomer(customer);
		invoice.setRentalCar(rentalCar);
	}
	private void idCorrectionForGetById(Invoice invoice, GetInvoiceByIdDto getInvoiceByIdDto) {

		getInvoiceByIdDto.setCustomerId(invoice.getCustomer().getCustomerId());
		getInvoiceByIdDto.setRentalCarId(invoice.getRentalCar().getRentalCarId());
	}

	private List<ListInvoiceDto> idCorrectionGetAll(List<Invoice> result, List<ListInvoiceDto> response) {

		for (int i = 0; i < result.size(); i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
		for (int i = 0; i < result.size(); i++) {
			response.get(i).setRentalCarId(result.get(i).getRentalCar().getRentalCarId());
		}
		return response;

	}

	private Invoice checkIfInvoiceExists(int id) {
		
		Invoice invoice =this.invoiceDao.getByInvoiceId(id);
		
		if(invoice==null) {
			throw new BusinessException("Cannot find an invoice with this Id.");
		}
		
		return invoice;
	}
	
	private void checkIfCustomerExists(int customerId) {
		
		if(this.customerService.getById(customerId)==null) {
			throw new BusinessException("Cannot find a customer with this Id.");
		}

	}

	private void checkIfRentalCarExists(int rentalCarId) {
		
		if(this.customerService.getById(rentalCarId)==null) {
			throw new BusinessException("Cannot find a rentalCar with this Id.");
		}
	}
	
	private void checkInvoiceIfRentalCarExists(int rentalCarId) {
		
		if(this.invoiceDao.getByRentalCar_rentalCarId(rentalCarId)!=null) {
			throw new BusinessException("There is an invoice for car rental.");
		}	
	}

	
	private void checkIfInvoiceNoExists(String invoiceNo) {
		
		if(this.invoiceDao.existsByInvoiceNo(invoiceNo)) {
			throw new BusinessException("Such an invoice number exists.");
		}
	}

	private void invoiceTableSetColumns(Invoice invoice, CreateInvoiceRequest createInvoiceRequest) {

		invoice.setCreateDate(LocalDate.now());
		invoice.setRentDate(invoice.getRentalCar().getRentDate());
		invoice.setReturnDate(invoice.getRentalCar().getReturnDate());
		invoice.setNumberDays(dateBetweenCalculator(invoice));
		invoice.setRentTotalPrice(totalPriceCalculator(invoice));
	}

	private int dateBetweenCalculator(Invoice invoice) {
		
		Long dateBetween = ChronoUnit.DAYS.between(invoice.getRentDate(), invoice.getReturnDate());
		int numberDays=dateBetween.intValue();
		if(numberDays==0) {
			numberDays=1;
		}
		return numberDays;
	}

	private double totalPriceCalculator(Invoice invoice) {
		
		double rentDailyPrice = invoice.getRentalCar().getTotalPrice();
		int numberDays = invoice.getNumberDays();
		double rentPrice = rentDailyPrice*numberDays;
		
		double additionalServiceDailyPrice=0;
		List<ListOrderedAdditionalServiceDto> listOrderedAdditionalServiceDtos = this.orderedAdditionalServiceService.getOrderedAdditionalServiceByRentalCarId(invoice.getRentalCar().getRentalCarId()).getData();
		
		for (int i = 0; i < listOrderedAdditionalServiceDtos.size(); i++) {	
			GetAdditionalServiceByIdDto additionalService = this.additionalServiceService.getByAdditionalServiceId(listOrderedAdditionalServiceDtos.get(i).getAdditionalServiceId()).getData();
			additionalServiceDailyPrice+=additionalService.getDailyPrice();	
		}
		double additionalServicePrice = additionalServiceDailyPrice*numberDays;
		
		return additionalServicePrice+rentPrice;
	}

}
