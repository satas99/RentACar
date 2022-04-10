package com.turkcellcamp.rentacar.business.requests.creates;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

	@NotBlank
	@Size(min=4)
	private String cardOwnerName;
	
	@CreditCardNumber
	@NotBlank
	private String cardNumber;
	
	@NotNull
	private int cardCvvNumber;
}
