package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {
	private String companyName;
	private String taxNumber;
	@Email
	private String email;
	private String password;
}
