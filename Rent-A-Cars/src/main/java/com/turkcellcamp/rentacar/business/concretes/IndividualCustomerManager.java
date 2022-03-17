package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetIndividualCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetUserByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateUserRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.IndividualCustomerDao;
import com.turkcellcamp.rentacar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,ModelMapperService modelMapperService,UserService userService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService=userService;
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		var result = this.individualCustomerDao.findAll();
		
		List<ListIndividualCustomerDto> response = result.stream()
				.map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(response, "Success");
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest){
		
		checkIfMailExists(createIndividualCustomerRequest.getEmail());
		
		checkIfIdentityNumberExistsAndRegex(createIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfIndividualCustomerExists(id);
		
		this.individualCustomerDao.deleteById(id);
		
		return new SuccessResult("IndividualCustomer.Deleted");
	}

	@Override
	public Result update(int id, UpdateIndividualCustomerRequest updateindividualCustomerRequest) {
		
		checkIfIndividualCustomerExists(id);
		
		checkIfMailExists(updateindividualCustomerRequest.getEmail());
		
		IndividualCustomer individualCustomer =  this.individualCustomerDao.getByIndividualCustomerId(id);
		updateOperation(id,individualCustomer, updateindividualCustomerRequest);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Updated");

	}
	
	private void updateOperation(int id, IndividualCustomer individualCustomer,UpdateIndividualCustomerRequest updateindividualCustomerRequest) {
		
		GetUserByIdDto user = this.userService.getById(id).getData();
		UpdateUserRequest updateUserRequest = this.modelMapperService.forDto().map(user, UpdateUserRequest.class);
		updateUserRequest.setEmail(updateindividualCustomerRequest.getEmail());
		updateUserRequest.setPassword(updateindividualCustomerRequest.getPassword());
		
		individualCustomer.setFirstName(updateindividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(updateindividualCustomerRequest.getLastName());
		this.userService.update(id,updateUserRequest);
		
	}

	@Override
	public DataResult<GetIndividualCustomerByIdDto> getById(int id) {
		
		IndividualCustomer result = checkIfIndividualCustomerExists(id);
		GetIndividualCustomerByIdDto response = this.modelMapperService.forDto().map(result, GetIndividualCustomerByIdDto.class);
		
		return new SuccessDataResult<GetIndividualCustomerByIdDto>(response, "Success");
	}

	private IndividualCustomer checkIfIndividualCustomerExists(int id) {
		
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getByIndividualCustomerId(id);
		
		if(this.individualCustomerDao.getByIndividualCustomerId(id)==null) {
			throw new BusinessException("Cannot find a individual customer with this Id.");
		}
		return individualCustomer;
	}
	private boolean checkIfMailExists(String mail) {
		
		if(this.individualCustomerDao.findByEmail(mail)!=null) {
			throw new BusinessException("Such a mail exists.");
		}
		return true;
	}
	private boolean checkIfIdentityNumberExistsAndRegex(String ıdentityNumber) {
  
		if( this.individualCustomerDao.findByIdentityNumber(ıdentityNumber)!=null) {
			throw new BusinessException("Such a ıdentityNumber exists.");
		}
		return true;
	}

	
	
}
