package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CarMaintenanceDao;
import com.turkcellcamp.rentacar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentalCarService rentalCarService;

	@Autowired
	public CarMaintenanceManager(@Lazy RentalCarService rentalCarService, @Lazy CarService carService,CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentalCarService = rentalCarService;
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());
		
		response = idCorrection(response, result);
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest){
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);

		checkMaintenanceIfCarExists(carMaintenance.getCarMaintenanceId());
		
		checkIfRentalCarExists(carMaintenance);
		
		carMaintenance.setCarMaintenanceId(0);
		
		checkIfCarExists(carMaintenance.getCar().getCarId());
		
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CARMAINTENANCEADDED);
	}

	@Override
	public Result update(int id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		
		checkIfCarMaintenanceExists(id);
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
		updateOperation(carMaintenance, updateCarMaintenanceRequest);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(BusinessMessages.CARMAINTENANCEUPDATED);
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfCarMaintenanceExists(id);
		
		this.carMaintenanceDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.CARMAINTENANCEDELETED);
	}

	@Override
	public DataResult<GetCarMaintenanceByIdDto> getById(int carMaintenanceId) {
		
		CarMaintenance result = this.carMaintenanceDao.getByCarMaintenanceId(carMaintenanceId);
		
		GetCarMaintenanceByIdDto response = this.modelMapperService.forDto().map(result,GetCarMaintenanceByIdDto.class);

		response.setCarId(result.getCar().getCarId());
			
		return new SuccessDataResult<GetCarMaintenanceByIdDto>(response, BusinessMessages.SUCCESS);

	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(int id){
		
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_carId(id);
			
		List<ListCarMaintenanceDto> response = result.stream().map(
			carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
			.collect(Collectors.toList());
			
		response = idCorrection(response, result);
			
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, BusinessMessages.SUCCESS);
	}

	private boolean checkIfCarExists(int id){
		
		DataResult<GetCarByIdDto> result = this.carService.getById(id);
		
		if (!result.isSuccess()) {
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
		}
		return true;
	}

	private	CarMaintenance checkIfCarMaintenanceExists(int id){
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getByCarMaintenanceId(id) ;
		
		if (carMaintenance == null) {
			throw new BusinessException(BusinessMessages.CARMAINTENANCENOTFOUND);
		}
		return carMaintenance;
	}

	private void updateOperation(CarMaintenance carMaintenance,UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		
		carMaintenance.setDescription(updateCarMaintenanceRequest.getDescription());
		carMaintenance.setReturnDate(updateCarMaintenanceRequest.getReturnDate());

	}

	private boolean checkIfRentalCarExists(CarMaintenance carMaintenance){
		
		DataResult<List<ListRentalCarDto>> result = this.rentalCarService.getRentalByCarId(carMaintenance.getCar().getCarId());

		if (!result.isSuccess()) {
			return true;
		}
		for (ListRentalCarDto rentalCar : result.getData()) {

			if (rentalCar.getReturnDate() == null) {
				throw new BusinessException(BusinessMessages.CARMAINTENANCECARINRENT);
			}

		}
		return true;
	}

	private boolean checkMaintenanceIfCarExists(int id){
		
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_carId(id);
		
		if (result.isEmpty()) {
			return true;
		}
		
		for (CarMaintenance carMaintenance : result) {
			if (carMaintenance.getReturnDate() == null) {
				throw new BusinessException(BusinessMessages.CARMAINTENANCESTILLMAINTENANCED);
			}
		}
		return true;

	}

	private List<ListCarMaintenanceDto> idCorrection(List<ListCarMaintenanceDto> response, List<CarMaintenance> result) {
		
		for (int i = 0; i < result.size(); i++) {
			for (ListCarMaintenanceDto listCarMaintenanceDt : response) {
				if (result.get(i).getCarMaintenanceId() == listCarMaintenanceDt.getCarMaintenanceId()) {
					listCarMaintenanceDt.setCarId(result.get(i).getCar().getCarId());
				}
			}
		}
		return response;

	}
}
