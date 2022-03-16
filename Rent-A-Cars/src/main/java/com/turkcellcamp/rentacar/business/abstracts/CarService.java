package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CarService {
	DataResult<List<ListCarDto>> getAll();

	Result add(CreateCarRequest createCarRequest) throws BusinessException;

	DataResult<GetCarByIdDto> getById(int carId) throws BusinessException;

	Result update(int id, UpdateCarRequest updateCarRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;

	DataResult<List<GetCarByDailyPriceDto>> getCarByDailyPrice(double dailyPrice);

	DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize);

	DataResult<List<ListCarDto>> getAllSorted();
}
