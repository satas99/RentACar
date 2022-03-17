package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCorporateCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	DataResult<List<ListCorporateCustomerDto>> getAll();

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) ;

	DataResult<GetCorporateCustomerByIdDto> getById(int id) ;
	
	Result delete(int id);

	Result update(int id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest) ;
}
