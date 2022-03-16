package com.turkcellcamp.rentacar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	private String firstName;
	private String lastName;
	private String identityNumber;
	private String email;
	private String password;
}
