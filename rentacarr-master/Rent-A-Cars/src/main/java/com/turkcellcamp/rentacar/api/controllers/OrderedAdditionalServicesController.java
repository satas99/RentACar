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

import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.entities.concretes.OrderedAdditionalService;

@RestController
@RequestMapping("/api/orderedAdditionals")
public class OrderedAdditionalServicesController {

	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@GetMapping("/getall")
	DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		return this.orderedAdditionalServiceService.getAll();
	}

	@PostMapping("/add")
	Result add(@RequestBody CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestParam("orderedAdditionalServiceId") int id) throws BusinessException {
		return this.orderedAdditionalServiceService.delete(id);
	}
	
	@PutMapping("/update")
	Result update(@RequestParam("orderedAdditionalServiceId") int id, @RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.update(id, updateOrderedAdditionalServiceRequest);
	}
	
	@GetMapping("/getid/{orderedAdditionalServiceId}")
	DataResult<GetOrderedAdditionalServiceByIdDto> getByOrderedAdditionalServiceId(@RequestParam("orderedAdditionalServiceId") int orderedadditionalServiceId) throws BusinessException{
		return this.orderedAdditionalServiceService.getByOrderedAdditionalServiceId(orderedadditionalServiceId);
	}

}
