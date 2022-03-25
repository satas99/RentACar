package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CarDao;
import com.turkcellcamp.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private ColorService colorService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, BrandService brandService,ColorService colorService) {
		
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService;
		this.colorService = colorService;
	}

	@Override
	public DataResult<List<ListCarDto>> getAll() {
		
		var result = this.carDao.findAll();
		
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest){

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		checkIfBrandExists(car.getBrand().getBrandId());
		
		checkIfColorExists(car.getColor().getColorId());
		
		this.carDao.save(car);
		
		return new SuccessResult(BusinessMessages.CARADDED);
	}

	@Override
	public Result update(int id, UpdateCarRequest updateCarRequest){
		
		checkIfCarExists(id);
		
		Car car = this.carDao.getByCarId(id);
		
		checkIfBrandExists(car.getBrand().getBrandId());
		
		checkIfColorExists(car.getColor().getColorId());
		
		updateOperation(car, updateCarRequest);
		
		this.carDao.save(car);
		
		return new SuccessResult(BusinessMessages.CARUPDATED);
	}

	@Override
	public Result delete(int id){
		
		checkIfCarExists(id);
		
		this.carDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.CARDELETED);
	}

	@Override
	public DataResult<GetCarByIdDto> getById(int carId){
		
		Car result = checkIfCarExists(carId);
		GetCarByIdDto response = this.modelMapperService.forDto().map(result, GetCarByIdDto.class);
			
		return new SuccessDataResult<GetCarByIdDto>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<List<GetCarByDailyPriceDto>> getCarByDailyPrice(double dailyPrice) {
		
		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		List<GetCarByDailyPriceDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, GetCarByDailyPriceDto.class)).collect(Collectors.toList());
			
		return new SuccessDataResult<List<GetCarByDailyPriceDto>>(response, BusinessMessages.SUCCESS);
		}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted() {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "dailyPrice");
		List<Car> result = this.carDao.findAll(sort);	
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, BusinessMessages.SUCCESS);
	}

	public Car checkIfCarExists(int carId){
		
		Car car = this.carDao.getByCarId(carId);
		
		if (car == null) {
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
		} else {
			return car;
		}
	}

	private boolean checkIfBrandExists(int id) {
		
		if (this.brandService.getById(id)==null) {
			throw new BusinessException(BusinessMessages.BRANDNOTFOUND);
		}
		return true;
	}

	private boolean checkIfColorExists(int id){
		
		if (this.colorService.getById(id)==null) {
			throw new BusinessException(BusinessMessages.COLORNOTFOUND);
		}
		return true;
	}

	private void updateOperation(Car car, UpdateCarRequest updateCarRequest) {
		
		car.setDailyPrice(updateCarRequest.getDailyPrice());
		car.setDescription(updateCarRequest.getDescription());
	}
	
}
