package com.turkcellcamp.rentacar.core.PosServices;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;

@Service
public class PosIsBankAdapter implements HalkBankService {

	@Override
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		Isbank ısBank = new Isbank();
		
		ısBank.payments(cardOwnerName, cardNumber, cardCvvNumber);
		
		return true;
	}

}
