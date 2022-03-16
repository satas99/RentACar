package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.lists.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	DataResult<List<ListCorporateCustomerDto>> getAll();

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

//	Result update(int id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;
}
