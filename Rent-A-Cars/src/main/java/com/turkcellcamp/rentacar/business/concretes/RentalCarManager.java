package com.turkcellcamp.rentacar.business.concretes;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateLateDeliveriesPaymentRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateLateDeliveriesRentalCarRequest;
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

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private PaymentService paymentService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	@Lazy
	public RentalCarManager(RentalCarDao rentalCarDao, CarMaintenanceService carMaintenanceService, CarService carService, ModelMapperService modelMapperService, CustomerService customerService, PaymentService paymentService,IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService) {

		this.rentalCarDao = rentalCarDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.paymentService = paymentService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public DataResult<List<ListRentalCarDto>> getAll() {

		var result = this.rentalCarDao.findAll();

		List<ListRentalCarDto> response = result.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class)).collect(Collectors.toList());

		idCorrectionForGetAll(result,response);
		
		return new SuccessDataResult<List<ListRentalCarDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public RentalCar addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest) {

		checkIfCarExists(createRentalCarRequest.getCarId());
		
		checkIfIndividualCustomerExists(createRentalCarRequest.getCustomerId());

		checkIfCarMaintenanceExists(createRentalCarRequest.getCarId(), createRentalCarRequest.getRentDate());

		checkRentalIfCarExists(createRentalCarRequest.getCarId(), createRentalCarRequest.getRentDate());

		checkRentDateIsBeforeReturnDate(createRentalCarRequest);
		
		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

		rentalCar.setRentalCarId(0);

		rentalCar.setTotalPrice(totalPriceCalculator(rentalCar));

		rentalCar.setCustomer(customerCorrectionAndCheckIfCustomerExists(createRentalCarRequest.getCustomerId()));

		rentalCar.setStartingKilometer(this.carService.getById(createRentalCarRequest.getCarId()).getData().getKilometerInfo());

		this.rentalCarDao.save(rentalCar);

		return rentalCar;
	}

	@Override
	public RentalCar addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest) {

		checkIfCarExists(createRentalCarRequest.getCarId());

		checkIfCorporateCustomerExists(createRentalCarRequest.getCustomerId());
		
		checkIfCarMaintenanceExists(createRentalCarRequest.getCarId(), createRentalCarRequest.getRentDate());

		checkRentalIfCarExists(createRentalCarRequest.getCarId(), createRentalCarRequest.getRentDate());
		
		checkRentDateIsBeforeReturnDate(createRentalCarRequest);

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

		rentalCar.setRentalCarId(0);

		rentalCar.setTotalPrice(totalPriceCalculator(rentalCar));

		rentalCar.setCustomer(customerCorrectionAndCheckIfCustomerExists(createRentalCarRequest.getCustomerId()));

		rentalCar.setStartingKilometer(
				this.carService.getById(createRentalCarRequest.getCarId()).getData().getKilometerInfo());

		this.rentalCarDao.save(rentalCar);

		return rentalCar;
	}

	@Override
	public Result update(int id, UpdateRentalCarRequest updateRentalCarRequest) {

		checkIfRentalExists(id);

		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);
		
		checkIfDates(rentalCar, updateRentalCarRequest);

		updateTotalPrice(rentalCar, updateRentalCarRequest);

		rentalCar.getCar().setKilometerInfo(updateRentalCarRequest.getReturnKilometer());

		updateOperation(rentalCar, updateRentalCarRequest);
		rentalCarDao.save(rentalCar);

		return new SuccessResult(BusinessMessages.RENTALCARUPDATED);
	}
	@Transactional
	@Override
	public Result lateDeliveriesUpdate(int id, UpdateLateDeliveriesRentalCarRequest updateLateDeliveriesRentalCarRequest) {

		checkIfRentalExists(id);
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);

		checkIfDates(rentalCar, updateLateDeliveriesRentalCarRequest.getUpdateRentalCarRequest());
		
		CreateLateDeliveriesPaymentRequest createLateDeliveriesPaymentRequest = createPayment(id);

		createLateDeliveriesPaymentRequest.setCreateCreditCard(createCreditCard(updateLateDeliveriesRentalCarRequest));
	
		updateTotalPrice(rentalCar, updateLateDeliveriesRentalCarRequest.getUpdateRentalCarRequest());

		rentalCar.getCar().setKilometerInfo(updateLateDeliveriesRentalCarRequest.getUpdateRentalCarRequest().getReturnKilometer());

		updateOperation(rentalCar, updateLateDeliveriesRentalCarRequest.getUpdateRentalCarRequest());
		rentalCarDao.save(rentalCar);
		
		this.paymentService.addForLateDelivery(createLateDeliveriesPaymentRequest);

		return new SuccessResult(BusinessMessages.RENTALCARUPDATED);
	}

	@Override
	public Result delete(int id) {

		checkIfRentalExists(id);

		this.rentalCarDao.deleteById(id);

		return new SuccessResult(BusinessMessages.RENTALCARDELETED);

	}

	@Override
	public DataResult<GetRentalCarByIdDto> getById(int rentalCarId) {

		RentalCar result = checkIfRentalExists(rentalCarId);

		GetRentalCarByIdDto response = this.modelMapperService.forRequest().map(result, GetRentalCarByIdDto.class);
		
		idCorrectionForGetById(result, response);

		response.setTotalDailyPrice(result.getTotalPrice());

		return new SuccessDataResult<GetRentalCarByIdDto>(response, BusinessMessages.SUCCESS);

	}

	@Override
	public DataResult<List<ListRentalCarDto>> getRentalByCarId(int id) {

		checkIfCarExists(id);

		List<RentalCar> result = this.rentalCarDao.getByCar_carId(id);
		
		List<ListRentalCarDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class))
				.collect(Collectors.toList());
		
		idCorrectionForGetAll(result,response);
		
		return new SuccessDataResult<List<ListRentalCarDto>>(response, BusinessMessages.SUCCESS);
	}
	
	private CreateCreditCardRequest createCreditCard(UpdateLateDeliveriesRentalCarRequest updateLateDeliveriesRentalCarRequest) {
		
		CreateCreditCardRequest createCreditCardRequest = new CreateCreditCardRequest();
		createCreditCardRequest.setCardOwnerName(updateLateDeliveriesRentalCarRequest.getCreateCreditCardRequest().getCardOwnerName());
		createCreditCardRequest.setCardCvvNumber(updateLateDeliveriesRentalCarRequest.getCreateCreditCardRequest().getCardCvvNumber());
		createCreditCardRequest.setCardNumber(updateLateDeliveriesRentalCarRequest.getCreateCreditCardRequest().getCardNumber());
	
		return createCreditCardRequest;
	}
	
	private CreateLateDeliveriesPaymentRequest createPayment(int id) {
		
		CreateLateDeliveriesPaymentRequest createLateDeliveriesPaymentRequest = new CreateLateDeliveriesPaymentRequest();
		
		createLateDeliveriesPaymentRequest.setRentalCarId(id);
		
		return createLateDeliveriesPaymentRequest;
		
	}
	
	private void idCorrectionForGetById(RentalCar result, GetRentalCarByIdDto response) {
	
		response.setCustomerId(result.getCustomer().getCustomerId());	
	}

	private List<ListRentalCarDto> idCorrectionForGetAll(List<RentalCar> result, List<ListRentalCarDto> response) {

		for (int i = 0; i < result.size(); i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}

		return response;

	}
	private boolean checkIfCarMaintenanceExists(int carId, LocalDate rentDate) {

		DataResult<List<ListCarMaintenanceDto>> result = this.carMaintenanceService.getCarMaintenanceByCarId(carId);

		if (!result.isSuccess()) {
			return true;
		}

		for (ListCarMaintenanceDto listCarMaintenanceDto : result.getData()) {

			if (listCarMaintenanceDto.getReturnDate() == null) {

				throw new BusinessException(BusinessMessages.RENTALCARINMAINTENANCE);
			}

			else if (rentDate.isBefore(listCarMaintenanceDto.getReturnDate())) {

				throw new BusinessException(BusinessMessages.RENTALCARINMAINTENANCE);
			}
		}
		return true;
	}

	private boolean checkRentalIfCarExists(int carId, LocalDate rentDate) {

		List<RentalCar> result = this.rentalCarDao.getByCar_carId(carId);

		if (result.isEmpty()) {
			return true;
		}

		for (RentalCar oldRental : result) {
			if (rentDate.isBefore(oldRental.getReturnDate())) {
				throw new BusinessException(BusinessMessages.RENTALCARINRENT);
			}
		}
		return true;

	}

	private void checkIfCorporateCustomerExists(int customerId) {

		this.corporateCustomerService.getById(customerId);
	}
	
	private void checkIfIndividualCustomerExists(int customerId) {

		this.individualCustomerService.getById(customerId);
	}


	private RentalCar checkIfRentalExists(int id) {

		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);
		if (rentalCar == null) {
			throw new BusinessException(BusinessMessages.RENTALCARNOTFOUND);
		}
		return rentalCar;
	}

	private boolean checkIfCarExists(int id) {

		if (this.carService.getById(id) == null) {
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
		}
		return true;
	}

	private void updateOperation(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {

		rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
		rentalCar.setReturnKilometer(updateRentalCarRequest.getReturnKilometer());
	}

	private Customer customerCorrectionAndCheckIfCustomerExists(int customerId) {

		GetCustomerByIdDto getCustomerByIdDto = this.customerService.getById(customerId).getData();
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);

		return customer;

	}

	private double totalPriceCalculator(RentalCar rentalCar) {

		GetCarByIdDto car = this.carService.getById(rentalCar.getCar().getCarId()).getData();

		long dateBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate());
		dateBetween++;

		double rentPrice = car.getDailyPrice();
		double totalPrice = rentPrice * dateBetween;

		if (rentalCar.getRentCity().getCityId() != rentalCar.getReturnCity().getCityId()) {
			totalPrice = totalPrice + 750;
		}
		return totalPrice;

	}

	private void checkIfDates(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {
		
		if(rentalCar.getReturnDate().isAfter(updateRentalCarRequest.getReturnDate())) {
			throw new BusinessException(BusinessMessages.RENTALCARFAİLRETURNDATE);
		}
		
	}
	private void updateTotalPrice(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {

			double totalPrice = 0;
		
			long dateBetween = ChronoUnit.DAYS.between(rentalCar.getReturnDate(),updateRentalCarRequest.getReturnDate());
			
			GetCarByIdDto car = this.carService.getById(rentalCar.getCar().getCarId()).getData();

			double rentPrice = car.getDailyPrice();

			if (rentalCar.getRentCity().getCityId() != updateRentalCarRequest.getReturnCityId()) {
				totalPrice = totalPrice + 750;
			}

			totalPrice = rentPrice * dateBetween;

			rentalCar.setTotalPrice(totalPrice);
		} 
	
		private void checkRentDateIsBeforeReturnDate (CreateRentalCarRequest createRentalCarRequest) {
			
			if(createRentalCarRequest.getReturnDate().isBefore(createRentalCarRequest.getRentDate())) {
				throw new BusinessException(BusinessMessages.RENTALCARRENTDATEFAİLRETURNDATE);	
			}
		}

	
}
