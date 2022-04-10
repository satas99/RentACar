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

	Result add(CreateCarRequest createCarRequest);

	DataResult<GetCarByIdDto> getById(int id);

	Result update(int id, UpdateCarRequest updateCarRequest);

	Result delete(int id);
	
	DataResult<List<ListCarDto>> getByColor_ColorId(int id);
	
	DataResult<List<ListCarDto>> getByBrand_BrandId(int id);

	DataResult<List<GetCarByDailyPriceDto>> getCarByDailyPrice(double dailyPrice);

	DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize);

	DataResult<List<ListCarDto>> getAllSorted();
}
