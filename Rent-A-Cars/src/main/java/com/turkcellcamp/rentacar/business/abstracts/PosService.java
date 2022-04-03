package com.turkcellcamp.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface PosService {
	
	public boolean doPayment(String cardOwnerName, String cardNumber, int cardCvvNumber);
}
