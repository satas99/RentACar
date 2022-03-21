package com.turkcellcamp.rentacar.business.requests.creates;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	private String cardOwnerName;
	private String cardNumber;
	private int cardCvvNumber;
}
