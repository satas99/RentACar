package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;



public interface AdditionalServiceService {
	DataResult<List<ListAdditionalServiceDto>> getAll();

	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	Result update(int id,UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result delete(int id) ;
	
	DataResult<GetAdditionalServiceByIdDto> getById(int additionalServiceId);
}
