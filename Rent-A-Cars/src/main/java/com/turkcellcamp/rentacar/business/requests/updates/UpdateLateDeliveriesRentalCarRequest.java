package com.turkcellcamp.rentacar.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.turkcellcamp.rentacar.business.requests.creates.CreateCreditCardRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLateDeliveriesRentalCarRequest {
	private UpdateRentalCarRequest updateRentalCarRequest;
	
	private CreateCreditCardRequest createCreditCardRequest;
}
