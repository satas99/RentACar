package com.turkcellcamp.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.business.abstracts.CarAccidentService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarAccidentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarAccidentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carAccidents")
public class CarAccidentsController {
	
	private CarAccidentService carAccidentService;

	public CarAccidentsController(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarAccidentRequest createCarAccidentRequest) {
		return this.carAccidentService.add(createCarAccidentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("carAccidentId") int id, @RequestBody @Valid UpdateCarAccidentRequest updateCarAccidentRequest) {
		return this.carAccidentService.update(id,updateCarAccidentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam("carAccidentId") int id) {
		return this.carAccidentService.delete(id);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarAccidentDto>> getAll() {
		return this.carAccidentService.getAll();
	}
	
	@GetMapping("/getCarAccidentById")
	public DataResult<GetCarAccidentByIdDto> getById(@RequestParam("carAccidentId") @Valid int carAccidentId) {
		return this.carAccidentService.getById(carAccidentId);
	}
	
	@GetMapping("/getCarAccidentsByCarId")
	public DataResult<List<ListCarAccidentDto>> getCarAccidentsByCarId(@RequestParam("carId") @Valid int carId){
		return this.carAccidentService.getCarAccidentsByCarId(carId);
	}
	
}
