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

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetBrandByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListBrandDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateBrandRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListBrandDto>> getAll() {
		return this.brandService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest){
		return this.brandService.add(createBrandRequest);

	}

	@PutMapping("/update")
	public Result update(@RequestParam("brandId") int id ,@RequestBody @Valid UpdateBrandRequest updateBrandRequest){
		return this.brandService.update(id, updateBrandRequest);

	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam("brandId") int id){
		return this.brandService.delete(id);

	}

	@GetMapping("/getid/{brandId}")
	public DataResult<GetBrandByIdDto> getById(@RequestParam("brandId") @Valid int brandId){
		return this.brandService.getById(brandId);
	}

}

