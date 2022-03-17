package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCorporateCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CorporateCustomerDao;
import com.turkcellcamp.rentacar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService,UserService userService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		
		List<ListCorporateCustomerDto> response = result.stream()
				.map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer, ListCorporateCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCorporateCustomerDto>>(response, "Success");
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest){
		
		checkIfTaxNumberExists(createCorporateCustomerRequest.getTaxNumber());
		
		checkIfMailExists(createCorporateCustomerRequest.getEmail());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Added");
	}

	@Override
	public Result update(int id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		
		checkIfTaxNumberExists(updateCorporateCustomerRequest.getTaxNumber());
		
		checkIfMailExists(updateCorporateCustomerRequest.getEmail());
		
		checkIfCorporateCustomerExists(id);
		
		CorporateCustomer corporateCustomer =  this.corporateCustomerDao.getByCorporateCustomerId(id);
		updateOperation(id,corporateCustomer, updateCorporateCustomerRequest);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Updated");
	}

	@Override
	public Result delete(int id){
		
		checkIfCorporateCustomerExists(id);
		
		this.corporateCustomerDao.deleteById(id);
		
		return new SuccessResult("CorporateCustomer.Deleted");
	}

	@Override
	public DataResult<GetCorporateCustomerByIdDto> getById(int id) {
		
		CorporateCustomer result = checkIfCorporateCustomerExists(id);
		GetCorporateCustomerByIdDto response = this.modelMapperService.forDto().map(result, GetCorporateCustomerByIdDto.class);
		
		return new SuccessDataResult<GetCorporateCustomerByIdDto>(response, "Success");
	}
	

	private void updateOperation(int id,CorporateCustomer corporateCustomer,UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		
		GetUserByIdDto user = this.userService.getById(id).getData();
		UpdateUserRequest updateUserRequest = this.modelMapperService.forDto().map(user, UpdateUserRequest.class);
		updateUserRequest.setEmail(updateCorporateCustomerRequest.getEmail());
		updateUserRequest.setPassword(updateCorporateCustomerRequest.getPassword());
		
		corporateCustomer.setCompanyName(updateCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		this.userService.update(id,updateUserRequest);
		
	}
	
	private CorporateCustomer checkIfCorporateCustomerExists(int id) {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getByCorporateCustomerId(id);
		
		if(this.corporateCustomerDao.getByCorporateCustomerId(id)==null) {
			throw new BusinessException("Cannot find a corporate customer with this Id.");
		}
		return corporateCustomer;
	}
	
	private boolean checkIfMailExists(String mail) {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByEmail(mail);
		
		if(corporateCustomer!=null) {
			throw new BusinessException("Such a mail exists.");
		}
		return true;
	}
	
	private boolean checkIfTaxNumberExists(String taxNmuber) {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByTaxNumber(taxNmuber);
		
		if(corporateCustomer!=null) {
			throw new BusinessException("Such a tax number exists.");
		}
		return true;
	}


}
