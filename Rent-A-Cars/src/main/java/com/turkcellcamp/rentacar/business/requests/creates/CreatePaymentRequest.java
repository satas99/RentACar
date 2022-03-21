package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	
	@NotNull
	private String cardOwnerName;
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private int cardCvvNumber;
	
	@NotNull
	private int invoiceId;
	
	@NotNull
	private int orderedAdditionalServiceId;
}