package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateLateDeliveriesRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

public interface RentalCarService {

	RentalCar addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest);
	
	RentalCar addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest);

	Result update(int id, UpdateRentalCarRequest updateRentalCarRequest);
	
	Result lateDeliveriesUpdate(int id, UpdateLateDeliveriesRentalCarRequest updateLateDeliveriesRentalCarRequest);

	Result delete(int id);

	DataResult<List<ListRentalCarDto>> getAll();

	DataResult<List<ListRentalCarDto>> getRentalByCarId(int carId);
	
	DataResult<List<ListRentalCarDto>> getRentalByCustomerId(int id);

	DataResult<GetRentalCarByIdDto> getById(int rentalCarId);
}
