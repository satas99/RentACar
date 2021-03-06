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

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCarRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCarRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListCarDto>> getAll() {
		return this.carService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest){
		return this.carService.add(createCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("carId") int id, @RequestBody @Valid UpdateCarRequest updateCarRequest){
		return this.carService.update(id, updateCarRequest );
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam("carId") int id){
		return this.carService.delete(id);
	}
	
	@GetMapping("/getid/{carId}")
	public DataResult<GetCarByIdDto> getById(@RequestParam("carId") int carId){
		return this.carService.getById(carId);
	}
	
	@GetMapping("/getCarsByBrandId/{brandId}")
	public DataResult<List<ListCarDto>> getByBrand_BrandId(@RequestParam("brandId") @Valid int id){
		return this.carService.getByBrand_BrandId(id);
	}
	
	@GetMapping("/getCarsByColorId/{colorId}")
	public DataResult<List<ListCarDto>> getByColor_ColorId(@RequestParam("colorId") @Valid int id){
		return this.carService.getByColor_ColorId(id);
	}

	@GetMapping("/getCarByDailyPrice")
	public DataResult <List<GetCarByDailyPriceDto>> getCarByDailyPrice(@RequestParam @Valid double dailyPrice){
		return this.carService.getCarByDailyPrice(dailyPrice);
	}
	
	@GetMapping("/getAllPaged")
	public DataResult <List<ListCarDto>> getAllPaged(@RequestParam @Valid int pageNumber, @RequestParam @Valid int pageSize){
		return this.carService.getAllPaged(pageNumber, pageSize);
	}
	
	@GetMapping("/getAllSorted")
	public DataResult <List<ListCarDto>> getAllSorted(){
		return this.carService.getAllSorted();
	}
}
