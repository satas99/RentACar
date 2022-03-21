package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.NotNull;

import com.turkcellcamp.rentacar.entities.concretes.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	@NotNull
	private int invoiceId;
	
	@NotNull
	private int orderedAdditionalServiceId;
	
	@NotNull
	private CreateCreditCardRequest createCreditCard;
}