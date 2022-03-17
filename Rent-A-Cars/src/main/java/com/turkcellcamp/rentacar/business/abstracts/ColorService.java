package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetColorByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListColorDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateColorRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateColorRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface ColorService {
	DataResult<List<ListColorDto>> getAll();

	Result add(CreateColorRequest createColorRequest);

	DataResult<GetColorByIdDto> getById(int colorId);

	Result update(int id, UpdateColorRequest updateColorRequest);

	Result delete(int id);
}
