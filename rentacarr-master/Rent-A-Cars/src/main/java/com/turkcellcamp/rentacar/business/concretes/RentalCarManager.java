package com.turkcellcamp.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.RentalCarDao;
import com.turkcellcamp.rentacar.entities.concretes.Car;
import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, @Lazy CarMaintenanceService carMaintenanceService,@Lazy CarService carService, ModelMapperService modelMapperService,CustomerService customerService) {
		
		this.rentalCarDao = rentalCarDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
		
		checkRentalIfCarExists(createRentalCarRequest.getCarId());
		
		checkIfCarExists(createRentalCarRequest.getCarId());
		
		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
		checkIfCarMaintenanceExists(rentalCar);
		
		rentalCar.setRentalCarId(0);
		
		rentalCar.setTotalPrice(totalPriceCalculator(rentalCar));
		
		rentalCar.setCustomer(customerCorrection(createRentalCarRequest.getCustomerId()));
		
		this.rentalCarDao.save(rentalCar);
		
		return new SuccessResult("RentalCar.Added");
	}
	
	@Override
	public Result update(int id, UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
		
		checkIfRentalExists(id);
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(id);
			
		updateOperation(rentalCar, updateRentalCarRequest);
		rentalCarDao.save(rentalCar);
		
		return new SuccessResult("RentalCar.Updated");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfRentalExists(id);
		
		this.rentalCarDao.deleteById(id);
		
		return new SuccessResult("RentalCar.Deleted");

	}

	@Override
	public DataResult<List<ListRentalCarDto>> getAll() {
		
		var result = this.rentalCarDao.findAll();
	
		List<ListRentalCarDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalCarDto>>(response);
	}

	@Override
	public DataResult<GetRentalCarByIdDto> getByRentalId(int rentalCarId) throws BusinessException {
		
		RentalCar result = this.rentalCarDao.getByRentalCarId(rentalCarId);
		
		if (result != null) {
			GetRentalCarByIdDto response = this.modelMapperService.forRequest().map(result, GetRentalCarByIdDto.class);

			return new SuccessDataResult<GetRentalCarByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetRentalCarByIdDto>("Cannot find a rented with this Id.");
	}

	@Override
	public DataResult<List<ListRentalCarDto>> getRentalByCarId(int id) throws BusinessException {
		
		List<RentalCar> result = this.rentalCarDao.getByCar_carId(id);
		
		if (checkIfCarExists(id) && !result.isEmpty()) {
			List<ListRentalCarDto> response = result.stream()
					.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, ListRentalCarDto.class))
					.collect(Collectors.toList());
			
			return new SuccessDataResult<List<ListRentalCarDto>>(response, "Success");
		}
		return new ErrorDataResult<List<ListRentalCarDto>>("The car with this id does not car rented exist..");
	}

	private boolean checkIfCarMaintenanceExists(RentalCar rentalCar) throws BusinessException {
		
		DataResult<List<ListCarMaintenanceDto>> result = this.carMaintenanceService.getCarMaintenanceByCarId(rentalCar.getCar().getCarId());
		
		if (!result.isSuccess()) {
			return true;
		}
		
		for (ListCarMaintenanceDto listCarMaintenanceDto : result.getData()) {
			if (listCarMaintenanceDto.getReturnDate() == null) {
				throw new BusinessException("The car cannot be sent for rent because it is on maintenance.");
			}
		}
		return true;
	}

	private boolean checkRentalIfCarExists(int id) throws BusinessException {
		
		List<RentalCar> result = this.rentalCarDao.getByCar_carId(id);
		
		if (result.isEmpty()) {
			return true;
		}
		
		for (RentalCar rentalCar : result) {
			if (rentalCar.getReturnDate() == null) {
				throw new BusinessException("the car is still in rental");
			}
		}
		return true;

	}

	private boolean checkIfRentalExists(int id) throws BusinessException {
		
		if (this.rentalCarDao.getByRentalCarId(id) != null) {
			return true;
		}
		throw new BusinessException("The rented car with this id does not exist..");
	}

	private boolean checkIfCarExists(int id) throws BusinessException {
		
		DataResult<GetCarByIdDto> result = this.carService.getById(id);
		
		if (!result.isSuccess()) {
			throw new BusinessException("The car with this id does not exist..");
		}
		return true;
	}

	private void updateOperation(RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {
		
		rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
	}

	private Customer customerCorrection(int customerId) throws BusinessException {
		
		GetCustomerByIdDto getCustomerByIdDto = this.customerService.getById(customerId).getData();
		
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);
		
		return customer;
		
	}
	
	private double totalPriceCalculator(RentalCar rentalCar) throws BusinessException {
		
		GetCarByIdDto car = this.carService.getById(rentalCar.getCar().getCarId()).getData();
		
		long dateBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate());
		if(dateBetween==0) {
			dateBetween=1;
		}
		
		double rentPrice=car.getDailyPrice();
		double totalPrice=car.getDailyPrice()*dateBetween;
 
        if(rentalCar.getRentCity().getCityId()!=rentalCar.getReturnCity().getCityId()) {
        	totalPrice=totalPrice+750;
        }
        
    		return totalPrice;
    
	}


}
