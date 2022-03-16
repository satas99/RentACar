package com.turkcellcamp.rentacar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	private String firstName;
	private String lastName;
	private String identityNumber;
	private String email;
	private String password;
}
