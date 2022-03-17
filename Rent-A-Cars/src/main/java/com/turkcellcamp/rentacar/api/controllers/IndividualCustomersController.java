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

import com.turkcellcamp.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetIndividualCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}

	@GetMapping("/getall")
	DataResult<List<ListIndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}

	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}

	@GetMapping("/getid/{individualCustomerId}")
	DataResult<GetIndividualCustomerByIdDto> getById(@RequestParam("individualCustomerId") int id) {
		return this.individualCustomerService.getById(id);
	}

	@PutMapping("/update")
	Result update(@RequestParam("individualCustomerId") int id,
			@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(id, updateIndividualCustomerRequest);
	}

	@DeleteMapping("/delete")
	Result delete(int id) {
		return this.individualCustomerService.delete(id);
	}
}
