package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListUserDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateUserRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.UserDao;
import com.turkcellcamp.rentacar.entities.concretes.User;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
		
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListUserDto>> getAll() {

		var result = this.userDao.findAll();
		List<ListUserDto> response = result.stream()
				.map(user -> this.modelMapperService.forDto().map(user, ListUserDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListUserDto>>(response);
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) throws BusinessException {

		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);
		this.userDao.save(user);

		return new SuccessResult("User.Added");
	}

	@Override
	public DataResult<GetUserByIdDto> getById(int userId) {

		User result = this.userDao.getByUserId(userId);
		
		if (result != null) {
			
			GetUserByIdDto response = this.modelMapperService.forDto().map(result, GetUserByIdDto.class);

			return new SuccessDataResult<GetUserByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetUserByIdDto>("Cannot find a user with this Id.");
	}

	@Override
	public Result update(int id, UpdateUserRequest updateUserRequest) throws BusinessException {
		
		checkIfUserExists(id);
		
		User user = this.userDao.getByUserId(id);
		updateOperation(user, updateUserRequest);
		this.userDao.save(user);

		return new SuccessResult("User.Updated");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfUserExists(id);
		
		this.userDao.deleteById(id);

		return new SuccessResult("User.Deleted");
	}

	private void updateOperation(User user, UpdateUserRequest updateUserRequest) {

		user.setEmail(updateUserRequest.getEmail());
		user.setPassword(updateUserRequest.getPassword());

	}

	private boolean checkIfUserExists(int id) throws BusinessException {

		if (this.userDao.getByUserId(id) != null) {
			return true;
		}
		throw new BusinessException("Cannot find a user with this Id.");
	}

}
