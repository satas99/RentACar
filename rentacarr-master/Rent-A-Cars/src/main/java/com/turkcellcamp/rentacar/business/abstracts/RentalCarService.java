package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface RentalCarService {

	Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
	Result update(int id,UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
	Result delete(int id) throws BusinessException;
	DataResult <List<ListRentalCarDto>> getAll();
	DataResult<List<ListRentalCarDto>> getRentalByCarId(int carId) throws BusinessException;
	DataResult<GetRentalCarByIdDto> getByRentalId(int rentalCarId) throws BusinessException;
}
