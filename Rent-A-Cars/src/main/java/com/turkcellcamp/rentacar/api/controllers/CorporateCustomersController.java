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

import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCorporateCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}

	@GetMapping("/getall")
	DataResult<List<ListCorporateCustomerDto>> getAll() {
		return this.corporateCustomerService.getAll();
	}

	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest){
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@GetMapping("/getid/{corporateCustomerId}")
	DataResult<GetCorporateCustomerByIdDto> getById(@RequestParam("corporateCustomerId") int id){
		return this.corporateCustomerService.getById(id);
	}

	@PutMapping("/update")
	Result update(@RequestParam("corporateCustomerId") int id,@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		return this.corporateCustomerService.update(id, updateCorporateCustomerRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestParam("corporateCustomerId") int id){
		return this.corporateCustomerService.delete(id);
	}
}
