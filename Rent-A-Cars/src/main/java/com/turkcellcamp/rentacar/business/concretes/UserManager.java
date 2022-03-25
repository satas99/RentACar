package com.turkcellcamp.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
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
	public DataResult<GetUserByIdDto> getById(int userId) {

		User result = checkIfUserExists(userId);
		GetUserByIdDto response = this.modelMapperService.forDto().map(result, GetUserByIdDto.class);

		return new SuccessDataResult<GetUserByIdDto>(response, "Success");
	}

	@Override
	public Result update(int id, UpdateUserRequest updateUserRequest){
		
		checkIfUserExists(id);
		
		User user = this.userDao.getByUserId(id);
		updateOperation(user, updateUserRequest);
		this.userDao.save(user);

		return new SuccessResult(BusinessMessages.USERUPDATED);
	}

	private void updateOperation(User user, UpdateUserRequest updateUserRequest) {

		user.setEmail(updateUserRequest.getEmail());
		user.setPassword(updateUserRequest.getPassword());

	}

	private User checkIfUserExists(int id){
		
		User user = this.userDao.getByUserId(id);
		
		if (user != null) {
			throw new BusinessException(BusinessMessages.USERNOTFOUND);
		}
		return user;
	}

}
