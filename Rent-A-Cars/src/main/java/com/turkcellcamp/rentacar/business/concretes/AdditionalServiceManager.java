package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListBrandDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.AdditionalServiceDao;
import com.turkcellcamp.rentacar.entities.concretes.AdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.Brand;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
	private final AdditionalServiceDao additionalServiceDao;
	private final ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		
		var result = this.additionalServiceDao.findAll();
		List<ListAdditionalServiceDto> response = result.stream().map(additionalService -> this.modelMapperService
				.forDto().map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response, "Success");
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {

		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("AdditionalService added : " + additionalService.getAdditionalServiceName());
	}

	@Override
	public Result update(int id, UpdateAdditionalServiceRequest updateAdditionalServiceRequest)throws BusinessException {
		
		checkIfAdditionalServiceExists(id);
		
		AdditionalService additionalService = this.additionalServiceDao.getByAdditionalServiceId(id);
		updateOperation(additionalService, updateAdditionalServiceRequest);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("AdditionalService.Updated" + updateAdditionalServiceRequest.getServiceName());
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfAdditionalServiceExists(id);
		
		this.additionalServiceDao.deleteById(id);
		
		return new SuccessResult("AdditionalService.Deleted");
	}
	
	@Override
	public DataResult<GetAdditionalServiceByIdDto> getByAdditionalServiceId(int additionalServiceId) throws BusinessException {
		
		var result = this.additionalServiceDao.getByAdditionalServiceId(additionalServiceId);
		
		if(result!=null) {
			GetAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(result, GetAdditionalServiceByIdDto.class);
			
			 return new SuccessDataResult<GetAdditionalServiceByIdDto>(response, "Success");
		}
		return new ErrorDataResult<GetAdditionalServiceByIdDto>("Cannot find an additional service with this Id.");
	}

	private void updateOperation(AdditionalService additionalService,UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		
		additionalService.setAdditionalServiceName(updateAdditionalServiceRequest.getServiceName());
		additionalService.setDailyPrice(updateAdditionalServiceRequest.getDailyPrice());
	}

	private boolean checkIfAdditionalServiceExists(int id) throws BusinessException {
		
		if (this.additionalServiceDao.getByAdditionalServiceId(id) != null) {
			return true;
		}
		throw new BusinessException("Cannot find an additional service with this Id.");
	}



}
