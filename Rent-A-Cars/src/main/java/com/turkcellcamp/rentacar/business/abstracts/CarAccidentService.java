package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCarAccidentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarAccidentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CarAccidentService {
	
	Result add(CreateCarAccidentRequest createCarAccidentRequest);

	Result update(int id, UpdateCarAccidentRequest updateCarAccidentRequest);

	Result delete(int id);

	DataResult<List<ListCarAccidentDto>> getAll();

	DataResult<List<ListCarAccidentDto>> getCarAccidentsByCarId(int carId);

	DataResult<GetCarAccidentByIdDto> getById(int carAccidentId);
}
