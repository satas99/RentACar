package com.turkcellcamp.rentacar.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.business.outServices.HalkBank;

@Service
public class PosHalkBankAdapter implements PosService {

	@Override
	public boolean doPayment(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		HalkBank halkBank = new HalkBank();
		
		halkBank.makePayment(cardNumber, cardOwnerName, cardCvvNumber);
		
		return true;
	}

}
