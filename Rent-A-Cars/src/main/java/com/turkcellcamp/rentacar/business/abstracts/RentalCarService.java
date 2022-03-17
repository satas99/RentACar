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

	Result add(CreateRentalCarRequest createRentalCarRequest);

	Result update(int id, UpdateRentalCarRequest updateRentalCarRequest);

	Result delete(int id);

	DataResult<List<ListRentalCarDto>> getAll();

	DataResult<List<ListRentalCarDto>> getRentalByCarId(int carId);

	DataResult<GetRentalCarByIdDto> getById(int rentalCarId);
}
