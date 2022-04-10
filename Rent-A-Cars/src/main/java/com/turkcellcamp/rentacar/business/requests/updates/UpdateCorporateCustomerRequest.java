package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	@NotNull
	private String companyName;
	
	@NotNull
	private String taxNumber;
	
	@Email
	private String email;	
	
	@Size(min=6)
	private String password;
}
