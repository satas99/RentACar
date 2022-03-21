package com.turkcellcamp.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.CreditCardService;
import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.CreditCardDao;

import com.turkcellcamp.rentacar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {
	
	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService) {
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		
		CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);
		
		creditCard.setCreditCardId(0);
		
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult("CreditCard.Added");
	}
	
}
