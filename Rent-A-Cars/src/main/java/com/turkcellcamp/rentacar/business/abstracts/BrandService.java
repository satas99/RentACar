package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.turkcellcamp.rentacar.business.dtos.gets.GetBrandByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListBrandDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateBrandRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface BrandService {
	
	DataResult<List<ListBrandDto>> getAll();

	Result add(CreateBrandRequest createBrandRequest);

	DataResult<GetBrandByIdDto> getById(int brandId);

	Result update(int id, UpdateBrandRequest updateBrandRequest);

	Result delete(int id);
}
