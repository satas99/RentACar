package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetBrandByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListBrandDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateBrandRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateBrandRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
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
		
		return new SuccessDataResult<List<ListBrandDto>>(response, BusinessMessages.SUCCESS);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest){
		
		checkIfBrandNameExists(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandDao.save(brand);
		
		return new SuccessResult(BusinessMessages.BRANDADDED);
	}

	@Override
	public Result update(int id, UpdateBrandRequest updateBrandRequest){
		
		checkIfBrandExists(id);
		
		checkIfBrandNameExists(updateBrandRequest.getBrandName());
		
		Brand brand = this.brandDao.getByBrandId(id);
		updateOperation(brand, updateBrandRequest);
		this.brandDao.save(brand);
		
		return new SuccessResult(BusinessMessages.BRANDUPDATED);
	}

	@Override
	public Result delete(int id){
		
		checkIfBrandExists(id);
		
		this.brandDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.BRANDDELETED);

	}

	public DataResult<GetBrandByIdDto> getById(int brandId) {
		
		Brand result = checkIfBrandExists(brandId);
		GetBrandByIdDto response = this.modelMapperService.forRequest().map(result, GetBrandByIdDto.class);
			
		return new SuccessDataResult<GetBrandByIdDto>(response, BusinessMessages.SUCCESS);

	}

	private boolean checkIfBrandNameExists(String brandName){
		
		if (this.brandDao.getByBrandName(brandName) == null) {
			return true;
		}
		throw new BusinessException(BusinessMessages.BRANDEXISTS);

	}

	private Brand checkIfBrandExists(int brandId){
		
		Brand brand = this.brandDao.getByBrandId(brandId) ;
		
		if (brand== null) {
			throw new BusinessException(BusinessMessages.BRANDNOTFOUND);
		}
		return brand;
	}

	private void updateOperation(Brand brand, UpdateBrandRequest updateBrandRequest) {
		
		brand.setBrandName(updateBrandRequest.getBrandName());

	}

}
