package com.turkcellcamp.rentacar.core.PosServices;

import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface PosService {
	
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber);
}
