package com.turkcellcamp.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface UserService {

	DataResult<GetUserByIdDto> getById(int userId);

	Result update(int id, UpdateUserRequest updateUserRequest);

}
