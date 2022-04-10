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

import com.turkcellcamp.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		return this.carMaintenanceService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest){
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestParam("carMaintenanceId") int id, @RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		return this.carMaintenanceService.update(id, updateCarMaintenanceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam("carMaintenanceId") int id){
		return this.carMaintenanceService.delete(id);
	}

	@GetMapping("/getid/{carMaintenanceId}")
	public DataResult<GetCarMaintenanceByIdDto> getById(@RequestParam("carMaintenanceId") @Valid int id){
		return this.carMaintenanceService.getById(id);
	}
	@GetMapping("/getCarMaintenancesByCarId/{carId}")
	DataResult<List<ListCarMaintenanceDto>> getCarMaintenanceByCarId(@RequestParam("carId") @Valid int id){
		return this.carMaintenanceService.getCarMaintenanceByCarId(id);
	}
}
