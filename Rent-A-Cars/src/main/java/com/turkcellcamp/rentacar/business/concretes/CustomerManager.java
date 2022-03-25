package com.turkcellcamp.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CustomerDao;
import com.turkcellcamp.rentacar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {
	private CustomerDao customerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<GetCustomerByIdDto> getById(int id){

		Customer result = checkIfCustomerExists(id);
		GetCustomerByIdDto response = this.modelMapperService.forDto().map(result, GetCustomerByIdDto.class);

		return new SuccessDataResult<GetCustomerByIdDto>(response, BusinessMessages.SUCCESS);
	}

	private Customer checkIfCustomerExists(int id) {
		Customer customer = this.customerDao.getByCustomerId(id);

		if (customer == null) {
			throw new BusinessException(BusinessMessages.CUSTOMERNOTFOUND);
		}
		return customer;
	}
}
