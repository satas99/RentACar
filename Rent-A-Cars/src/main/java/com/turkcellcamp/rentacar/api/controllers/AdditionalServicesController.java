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

import com.turkcellcamp.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;


@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServicesController {
	private AdditionalServiceService additionalServiceService;
	
	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}

	@GetMapping("/getall")
	DataResult<List<ListAdditionalServiceDto>> getAll(){
		return this.additionalServiceService.getAll();
	}


    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest){
        return this.additionalServiceService.add(createAdditionalServiceRequest);
    }
	@PutMapping("/update")
	Result update(@RequestParam("additionalServiceId") int id, @RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
		return this.additionalServiceService.update(id, updateAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	Result delete(@RequestParam("additionalServiceId") int id){
		return this.additionalServiceService.delete(id);
	}
	@GetMapping("/getid/{additionalServiceId}")
	DataResult<GetAdditionalServiceByIdDto> getByAdditionalServiceId(@RequestParam("additionalServiceId") @Valid int additionalServiceId){
		return this.additionalServiceService.getByAdditionalServiceId(additionalServiceId);
	}
}
