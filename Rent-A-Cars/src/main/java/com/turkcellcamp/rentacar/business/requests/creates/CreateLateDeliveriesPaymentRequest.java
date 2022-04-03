package com.turkcellcamp.rentacar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLateDeliveriesPaymentRequest {

	private int rentalCarId;

	private CreateCreditCardRequest createCreditCard;
}
