package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetIndividualCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface IndividualCustomerService {
	DataResult<List<ListIndividualCustomerDto>> getAll();

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

	DataResult<GetIndividualCustomerByIdDto> getById(int id);

	Result delete(int id);

	Result update(int id, UpdateIndividualCustomerRequest updateindividualCustomerRequest);

	boolean checkIndividualCustomerIfForRental(int id);
}
