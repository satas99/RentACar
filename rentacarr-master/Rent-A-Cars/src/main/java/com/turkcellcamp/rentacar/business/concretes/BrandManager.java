package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetBrandByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListBrandDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateBrandRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.BrandDao;
import com.turkcellcamp.rentacar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
	
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListBrandDto>> getAll() {
		
		var result = this.brandDao.findAll();
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListBrandDto>>(response, "Success");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		
		checkIfBrandName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandDao.save(brand);
		
		return new SuccessResult("Brand.Added ");

	}

	public DataResult<GetBrandByIdDto> getById(int brandId) {
		
		var result = this.brandDao.getByBrandId(brandId);
		
		if (result != null) {
			GetBrandByIdDto response = this.modelMapperService.forRequest().map(result, GetBrandByIdDto.class);
			
			return new SuccessDataResult<GetBrandByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetBrandByIdDto>("Cannot find a brand with this Id.");

	}

	@Override
	public Result update(int id, UpdateBrandRequest updateBrandRequest) throws BusinessException {
		
		checkIfBrandExists(id);
		
		checkIfBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.brandDao.getByBrandId(id);
		updateOperation(brand, updateBrandRequest);
		this.brandDao.save(brand);
		
		return new SuccessResult("Brand.Updated");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfBrandExists(id);
		
		this.brandDao.deleteById(id);
		
		return new SuccessResult("Brand.Deleted ");

	}

	private boolean checkIfBrandName(String brandName) throws BusinessException {
		
		if (this.brandDao.getByBrandName(brandName) == null) {
			return true;
		}
		throw new BusinessException("Such a brand exists.");

	}

	private boolean checkIfBrandExists(int brandId) throws BusinessException {
		
		if (this.brandDao.getByBrandId(brandId) != null) {
			return true;
		}
		throw new BusinessException("Cannot find a brand with this Id.");
	}

	private void updateOperation(Brand brand, UpdateBrandRequest updateBrandRequest) {
		
		brand.setBrandName(updateBrandRequest.getBrandName());

	}

}
