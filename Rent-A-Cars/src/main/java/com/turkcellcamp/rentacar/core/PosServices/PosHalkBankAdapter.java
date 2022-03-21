package com.turkcellcamp.rentacar.core.PosServices;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;

@Service
public class PosHalkBankAdapter implements IsBankService {

	@Override
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		HalkBank halkBank = new HalkBank();
		
		halkBank.payments(cardNumber, cardOwnerName, cardCvvNumber);
		
		return true;
	}

}
