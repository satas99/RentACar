package com.turkcellcamp.rentacar.business.adapters;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.business.outServices.Isbank;

@Service
@Primary
public class PosIsBankAdapter implements PosService {

	@Override
	public boolean doPayment(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		Isbank ısBank = new Isbank();
		
		ısBank.makePayment(cardOwnerName, cardNumber, cardCvvNumber);
		
		return true;
	}

}
