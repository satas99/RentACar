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

import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentalcars")
public class RentalCarsController {
	private RentalCarService rentalCarService;

	@Autowired
	public RentalCarsController(RentalCarService rentalCarService) {
		this.rentalCarService = rentalCarService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
		return this.rentalCarService.add(createRentalCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("rentalId") int id,@RequestBody @Valid UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException{
		return this.rentalCarService.update(id,updateRentalCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam("rentalId") int id) throws BusinessException{
		return this.rentalCarService.delete(id);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListRentalCarDto>> getAll() {
		return this.rentalCarService.getAll();
	}
	@GetMapping("/getRentalByCarId/{carId}")
	DataResult<List<ListRentalCarDto>> getRentalByCarId(@RequestParam("carId") @Valid int carId) throws BusinessException{
		return this.rentalCarService.getRentalByCarId(carId);
	}
	@GetMapping("/getByRentalId/{rentalId}")
	DataResult<GetRentalCarByIdDto> getByRentalId(@RequestParam("rentalId") @Valid int rentalId) throws BusinessException{
		return this.rentalCarService.getByRentalId(rentalId);
	}
	
	
}