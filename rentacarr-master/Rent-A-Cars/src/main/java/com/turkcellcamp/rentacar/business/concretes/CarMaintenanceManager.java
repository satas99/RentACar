package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
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
		
		response = carCorrection(response, result);
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);

		checkMaintenanceIfCarExists(carMaintenance.getCarMaintenanceId());
		
		checkIfRentalCarExists(carMaintenance);
		
		carMaintenance.setCarMaintenanceId(0);
		
		checkIfCarExists(carMaintenance.getCar().getCarId());
		
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult("CarMaintenance.Added");
	}

	@Override
	public Result update(int id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceExists(id);
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
		updateOperation(carMaintenance, updateCarMaintenanceRequest);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Updated");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfCarMaintenanceExists(id);
		
		this.carMaintenanceDao.deleteById(id);
		
		return new SuccessResult("CarMaintenance.Deleted");
	}

	@Override
	public DataResult<GetCarMaintenanceByIdDto> getById(int carMaintenanceId) {
		
		CarMaintenance result = this.carMaintenanceDao.getByCarMaintenanceId(carMaintenanceId);
		
		if (result != null) {
			GetCarMaintenanceByIdDto response = this.modelMapperService.forDto().map(result,GetCarMaintenanceByIdDto.class);

			response.setCarId(result.getCar().getCarId());
			
			return new SuccessDataResult<GetCarMaintenanceByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetCarMaintenanceByIdDto>("Cannot find a car maintenance with this Id.");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(int id) throws BusinessException {
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_carId(id);
		
		if (checkIfCarExists(id) && !result.isEmpty()) {
			
			List<ListCarMaintenanceDto> response = result.stream().map(
					carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
					.collect(Collectors.toList());
			
			response = carCorrection(response, result);
			
			return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, "Success");
		}
		return new ErrorDataResult<List<ListCarMaintenanceDto>>(
				"The car with this id does not car maintenance exist..");
	}

	private boolean checkIfCarExists(int id) throws BusinessException {
		
		DataResult<GetCarByIdDto> result = this.carService.getById(id);
		
		if (!result.isSuccess()) {
			throw new BusinessException("The car with this id does not exist..");
		}
		return true;
	}

	private boolean checkIfCarMaintenanceExists(int id) throws BusinessException {
		
		if (this.carMaintenanceDao.getByCarMaintenanceId(id) == null) {
			throw new BusinessException("The car maintenance with this id does not exist..");
		}
		return true;
	}

	private void updateOperation(CarMaintenance carMaintenance,UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		
		carMaintenance.setDescription(updateCarMaintenanceRequest.getDescription());
		carMaintenance.setReturnDate(updateCarMaintenanceRequest.getReturnDate());

	}

	private boolean checkIfRentalCarExists(CarMaintenance carMaintenance) throws BusinessException {
		
		DataResult<List<ListRentalCarDto>> result = this.rentalCarService.getRentalByCarId(carMaintenance.getCar().getCarId());

		if (!result.isSuccess()) {
			return true;
		}
		for (ListRentalCarDto rentalCar : result.getData()) {

			if (rentalCar.getReturnDate() == null) {
				throw new BusinessException("The car cannot be sent for maintenance because it is on rent.");
			}

		}
		return true;
	}

	private boolean checkMaintenanceIfCarExists(int id) throws BusinessException {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_carId(id);
		
		if (result.isEmpty()) {
			return true;
		}
		
		for (CarMaintenance carMaintenance : result) {
			if (carMaintenance.getReturnDate() == null) {
				throw new BusinessException("the car is still in maintenance");
			}
		}
		return true;

	}

	private List<ListCarMaintenanceDto> carCorrection(List<ListCarMaintenanceDto> response, List<CarMaintenance> result) {
		
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
