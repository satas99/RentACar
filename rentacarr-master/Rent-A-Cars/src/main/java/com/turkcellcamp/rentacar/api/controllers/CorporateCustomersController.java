package com.turkcellcamp.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
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
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}

	/*
	 * @PutMapping("/update") Result update(int id, UpdateCorporateCustomerRequest
	 * updateCorporateCustomerRequest) throws BusinessException { return
	 * this.corporateCustomerService.update(id, updateCorporateCustomerRequest); }
	 */
	@DeleteMapping("/delete")
	Result delete(int id) throws BusinessException {
		return this.corporateCustomerService.delete(id);
	}
}
