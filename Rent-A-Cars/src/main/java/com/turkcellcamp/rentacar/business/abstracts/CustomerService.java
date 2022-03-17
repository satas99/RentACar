package com.turkcellcamp.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;

public interface CustomerService {

	DataResult<GetCustomerByIdDto> getById(int id);
}
