package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CarAccidentService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarAccidentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarAccidentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CarAccidentDao;
import com.turkcellcamp.rentacar.entities.concretes.CarAccident;

@Service
public class CarAccidentManager implements CarAccidentService {

	private CarAccidentDao carAccidentDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	
	@Autowired
	public CarAccidentManager(CarAccidentDao carAccidentDao, ModelMapperService modelMapperService,CarService carService) {
		this.carAccidentDao = carAccidentDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}
	
	@Override
	public DataResult<List<ListCarAccidentDto>> getAll() {
		
		var result = this.carAccidentDao.findAll();
		
		List<ListCarAccidentDto> response = result.stream().map(carAccident ->this.modelMapperService.forDto()
				.map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());
		
		response = ıdCorrectionForGetAll(result, response);
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(CreateCarAccidentRequest createCarAccidentRequest) {
		
		checkIfCarExists(createCarAccidentRequest.getCarId());
		
		CarAccident carAccident = this.modelMapperService.forRequest().map(createCarAccidentRequest, CarAccident.class);
		
		carAccident.setCarAccidentId(0);
		
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult(BusinessMessages.CARACCİDENTADDED);
	}

	@Override
	public Result update(int id, UpdateCarAccidentRequest updateCarAccidentRequest) {
		
		checkIfCarAccidentExists(id);
		
		CarAccident carAccident = this.carAccidentDao.getByCarAccidentId(id);
		updateOperation(carAccident, updateCarAccidentRequest);
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult(BusinessMessages.CARACCİDENTUPDATED);
	}

	@Override
	public Result delete(int id) {

		checkIfCarAccidentExists(id);

		this.carAccidentDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.CARACCİDENTDELETED);
	}

	@Override
	public DataResult<List<ListCarAccidentDto>> getCarAccidentsByCarId(int carId) {
		
		checkIfCarExists(carId);
		
		List<CarAccident> result = this.carAccidentDao.getByCar_CarId(carId);
		
		List<ListCarAccidentDto> response = result.stream().map(carAccident ->this.modelMapperService.forDto()
				.map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());
		
		response = ıdCorrectionForGetAll(result, response);
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public DataResult<GetCarAccidentByIdDto> getById(int id) {

		CarAccident result = checkIfCarAccidentExists(id);
		
		GetCarAccidentByIdDto response = this.modelMapperService.forDto().map(result, GetCarAccidentByIdDto.class);
		response.setCarId(result.getCar().getCarId());
		
		return new SuccessDataResult<GetCarAccidentByIdDto>(response, BusinessMessages.SUCCESS);
	}
	

	private void updateOperation(CarAccident carAccident, UpdateCarAccidentRequest updateCarAccidentRequest) {
		
		carAccident.setCarAccidentDescription(updateCarAccidentRequest.getCarAccidentDescription());
		
	}
	
	private List<ListCarAccidentDto> ıdCorrectionForGetAll(List<CarAccident> result,List<ListCarAccidentDto> response) {
		
		for(int i = 0; i < result.size() ; i++) {
			response.get(i).setCarId(result.get(i).getCar().getCarId());		
		}
		return response;
	}
	
	private boolean checkIfCarExists(int carId) {
		
		if(this.carService.getById(carId).getData() == null) {
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
		}
		return true;
	}
	
	private CarAccident checkIfCarAccidentExists(int carAccidentId) {
		
		CarAccident carAccident = this.carAccidentDao.getByCarAccidentId(carAccidentId);
		
		if(carAccident == null) {
			throw new BusinessException(BusinessMessages.CARACCİDENTNOTFOUND);
		}
		return carAccident;
	}
	
}
