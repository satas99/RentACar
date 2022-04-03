package com.turkcellcamp.rentacar.business.requests.creates;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.turkcellcamp.rentacar.entities.concretes.AdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.CreditCard;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	private List<Integer> additionalServiceIds;

	private CreateRentalCarRequest rentalCar;

	private CreateCreditCardRequest createCreditCard;
}