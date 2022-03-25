package com.turkcellcamp.rentacar.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.RentalCarDao;
import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

import net.bytebuddy.utility.RandomString;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private InvoiceService invoiceService;
	private PaymentService paymentService;

	@Autowired
	@Lazy
	public RentalCarManager(RentalCarDao rentalCarDao, @Lazy CarMaintenanceService carMaintenanceService,@Lazy CarService carService, ModelMapperService modelMapperService,CustomerService customerService, @Lazy InvoiceService invoiceService, PaymentService paymentService) {
		
		this.rentalCarDao = rentalCarDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.invoiceService = invoiceService;
		this.paymentService = paymentService;
	}

	@Override
	public DataResult<List<ListRentalCarDto>> getAll() {
		
		var result = this.rentalCarDao.findAll();
	
		List<ListRentalCarDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalCarDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest){
		
		checkRentalIfCarExists(createRentalCarRequest.getCarId());
		
		checkIfCarExists(createRentalCarRequest.getCarId());
		
		//checkIfCustomerExists(createRentalCarRequest.getCustomerId());"ModelMapperı düzeltmek için Setcustomer işlemi yapıldığında customerın getById'sini kullanıyoruz ve 
		//orada kontrol yapıldığından dolayı tekrar kontrol yapmaya gerek yoktur."
		
		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
		checkIfCarMaintenanceExists(rentalCar);
		
		rentalCar.setRentalCarId(0);
		
		rentalCar.setTotalPrice(totalPriceCalculator(rentalCar));
		
		rentalCar.setCustomer(customerCorrection(createRentalCarRequest.getCustomerId()));//"Customer getById'yi kullandığı için checkIfCustomerExists kontrolü zaten orada yapılmaktadır."
		
		rentalCar.setStartingKilometer(this.carService.getById(createRentalCarRequest.getCarId()).getData().getKilometerInfo());
		
		
		this.rentalCarDao.save(rentalCar);
		
		return new SuccessResult(BusinessMessages.RENTALCARADDED);
	}

	@Override
	public Result update(int id, UpdateRentalCarRequest updateRentalCarRequest){
		
		checkIfRentalExists(id);
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);
		
		rentalCar.setTotalPrice(updateTotalPrice(rentalCar, updateRentalCarRequest)); 
		
		rentalCar.getCar().setKilometerInfo(updateRentalCarRequest.getReturnKilometer());
		
		updateOperation(rentalCar, updateRentalCarRequest);
		rentalCarDao.save(rentalCar);;
		
//		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
//		createInvoiceRequest.setCustomerId(rentalCar.getCustomer().getCustomerId());
//		createInvoiceRequest.setInvoiceNo(String.valueOf(id*10+RandomString.make()));
//		createInvoiceRequest.setRentalCarId(id);
//		this.invoiceService.add(createInvoiceRequest);
		
		return new SuccessResult(BusinessMessages.RENTALCARUPDATED);
	}

	@Override
	public Result delete(int id){
		
		checkIfRentalExists(id);
		
		this.rentalCarDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.RENTALCARDELETED);

	}

	@Override
	public DataResult<GetRentalCarByIdDto> getById(int rentalCarId){
		
		RentalCar result = checkIfRentalExists(rentalCarId);
		
		GetRentalCarByIdDto response = this.modelMapperService.forRequest().map(result, GetRentalCarByIdDto.class);
		
		response.setTotalDailyPrice(result.getTotalPrice());

		return new SuccessDataResult<GetRentalCarByIdDto>(response, BusinessMessages.SUCCESS);

	}

	@Override
	public DataResult<List<ListRentalCarDto>> getRentalByCarId(int id){
		
		checkIfCarExists(id);
		
		List<RentalCar> result = this.rentalCarDao.getByCar_carId(id);
		List<ListRentalCarDto> response = result.stream()
			.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class))
			.collect(Collectors.toList());
			
		return new SuccessDataResult<List<ListRentalCarDto>>(response, BusinessMessages.SUCCESS);
		}

	private boolean checkIfCarMaintenanceExists(RentalCar rentalCar){
		
		DataResult<List<ListCarMaintenanceDto>> result = this.carMaintenanceService.getCarMaintenanceByCarId(rentalCar.getCar().getCarId());
		
		if (!result.isSuccess()) {
			return true;
		}
		
		for (ListCarMaintenanceDto listCarMaintenanceDto : result.getData()) {
			if (listCarMaintenanceDto.getReturnDate() == null) {
				throw new BusinessException(BusinessMessages.RENTALCARINMAINTENANCE);
			}
		}
		return true;
	}

	private boolean checkRentalIfCarExists(int id){
		
		List<RentalCar> result = this.rentalCarDao.getByCar_carId(id);
		
		if (result.isEmpty()) {
			return true;
		}
		
		for (RentalCar rentalCar : result) {
			if (rentalCar.getReturnDate() == null) {
				throw new BusinessException(BusinessMessages.RENTALCARINRENT);
			}
		}
		return true;

	}

	private RentalCar checkIfRentalExists(int id){
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);
		if (rentalCar == null) {
			throw new BusinessException(BusinessMessages.RENTALCARNOTFOUND);
		}
		return rentalCar;
	}

	private boolean checkIfCarExists(int id){
		
		if (this.carService.getById(id)==null) {
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
		}
		return true;
	}

	private void updateOperation(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {
		
		rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
		rentalCar.setReturnKilometer(updateRentalCarRequest.getReturnKilometer());	
	}

	private Customer customerCorrection(int customerId){
		
		GetCustomerByIdDto getCustomerByIdDto = this.customerService.getById(customerId).getData();
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);
		
		return customer;
		
	}
	
	private double totalPriceCalculator(RentalCar rentalCar){
		
		GetCarByIdDto car = this.carService.getById(rentalCar.getCar().getCarId()).getData();
		
		long dateBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate());
		if(dateBetween==0) {
			dateBetween=1;
		}
		
		double rentPrice=car.getDailyPrice();
		double totalPrice=rentPrice*dateBetween;
 
        if(rentalCar.getRentCity().getCityId()!=rentalCar.getReturnCity().getCityId()) {
        	totalPrice=totalPrice+750;
        }
        
    		return totalPrice;
    
	}

	private double updateTotalPrice(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {

		long dateBetween = ChronoUnit.DAYS.between(rentalCar.getReturnDate(), updateRentalCarRequest.getReturnDate());
		
		GetCarByIdDto car = this.carService.getById(rentalCar.getCar().getCarId()).getData();
		
		double totalPrice=0;
		double rentPrice=car.getDailyPrice();
		
	    if(dateBetween!=0) {
	    	
	    if(rentalCar.getRentCity().getCityId()!=updateRentalCarRequest.getReturnCityId()) {
	        	totalPrice=totalPrice+750;	
		}
	    
		totalPrice=rentPrice*dateBetween;
	}	
	    System.out.println(totalPrice);
	    return totalPrice;
		


}
	}
