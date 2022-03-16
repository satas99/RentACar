package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListUserDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateUserRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface UserService {
	DataResult<List<ListUserDto>> getAll();

	Result add(CreateUserRequest createUserRequest) throws BusinessException;

	DataResult<GetUserByIdDto> getById(int userId);

	Result update(int id, UpdateUserRequest updateUserRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;
}
