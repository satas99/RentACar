package com.turkcellcamp.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface CreditCardService {
	
	Result add(CreateCreditCardRequest createCreditCardRequest);
}
