package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetColorByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListColorDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateColorRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateColorRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.ColorDao;
import com.turkcellcamp.rentacar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListColorDto>> getAll() {
		
		var result = this.colorDao.findAll();
		
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response, "Success");
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		checkIfColorName(color.getColorName());
		
		this.colorDao.save(color);
		
		return new SuccessResult("Color.Added ");

	}

	@Override
	public DataResult<GetColorByIdDto> getById(int colorId) {
		
		Color result = this.colorDao.getByColorId(colorId);
		
		if (result != null) {
			GetColorByIdDto response = this.modelMapperService.forDto().map(result, GetColorByIdDto.class);
			
			return new SuccessDataResult<GetColorByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetColorByIdDto>("Cannot find a color with this Id.");
	}

	@Override
	public Result update(int id, UpdateColorRequest updateColorRequest) throws BusinessException {
		
		checkIfColorExists(id);
		
		checkIfColorName(updateColorRequest.getColorName());
		
		Color color = this.colorDao.getByColorId(id);
		updateOperation(color, updateColorRequest);
		this.colorDao.save(color);
		
		return new SuccessResult("Color.Updated ");

	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfColorExists(id);
		
		this.colorDao.deleteById(id);
		
		return new SuccessResult("Color.Deleted");

	}

	private boolean checkIfColorName(String colorName) throws BusinessException {
		
		if (this.colorDao.getByColorName(colorName) == null) {
			return true;
		}
		throw new BusinessException("Such a color exists.");

	}

	private boolean checkIfColorExists(int colorId) throws BusinessException {
		
		if (this.colorDao.getByColorId(colorId) != null) {
			return true;
		}
		throw new BusinessException("Cannot find a color with this Id.");
	}

	private void updateOperation(Color color, UpdateColorRequest updateColorRequest) {
		
		color.setColorName(updateColorRequest.getColorName());

	}
}
