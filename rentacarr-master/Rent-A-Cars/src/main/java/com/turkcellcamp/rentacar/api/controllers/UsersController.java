package com.turkcellcamp.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListUserDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateUserRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	private UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/getall")
	DataResult<List<ListUserDto>> getAll() {
		return this.userService.getAll();
	}

	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateUserRequest createUserRequest) throws BusinessException {
		return this.userService.add(createUserRequest);
	}

	@GetMapping("/getid/{userId}")
	DataResult<GetUserByIdDto> getById(@RequestParam("userId") int userId) {
		return this.userService.getById(userId);
	}

	@PutMapping("/update")
	Result update(@RequestParam("userId") int id, @RequestBody @Valid UpdateUserRequest updateUserRequest)
			throws BusinessException {
		return this.userService.update(id, updateUserRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestParam("userId") int id) throws BusinessException {
		return this.userService.delete(id);
	}
}
