package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	private String companyName;
	private String taxNumber;
	@Email
	private String email;	
	private String password;
}
