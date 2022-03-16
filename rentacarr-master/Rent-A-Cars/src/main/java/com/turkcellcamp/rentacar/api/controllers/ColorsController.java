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

import com.turkcellcamp.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetColorByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListColorDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateColorRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateColorRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListColorDto>> getAll() {
		return this.colorService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		return this.colorService.add(createColorRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestParam("colorId") int id, @RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException {
		return this.colorService.update(id, updateColorRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam("colorId") int id) throws BusinessException {
		return this.colorService.delete(id);
	}

	@GetMapping("/getid/{colorId}")
	public DataResult<GetColorByIdDto> getById(@RequestParam("colorId") @Valid int colorId) throws BusinessException {
		return this.colorService.getById(colorId);
	}

}


