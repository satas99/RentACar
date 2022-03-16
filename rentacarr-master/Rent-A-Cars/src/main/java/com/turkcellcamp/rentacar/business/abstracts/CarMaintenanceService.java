package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {
	DataResult<List<ListCarMaintenanceDto>> getAll();

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

	Result update(int id,UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;

	DataResult<GetCarMaintenanceByIdDto> getById(int id);
	
	DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(int carMaintenanceId) throws BusinessException;

}
