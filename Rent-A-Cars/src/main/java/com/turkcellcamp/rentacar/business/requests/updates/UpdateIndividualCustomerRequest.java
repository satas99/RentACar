package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String password;
}
