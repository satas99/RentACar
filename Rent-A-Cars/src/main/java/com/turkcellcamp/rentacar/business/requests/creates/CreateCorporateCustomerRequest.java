package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {
	
	@NotBlank
	private String companyName;
	
	@NotBlank
	private String taxNumber;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min=6)
	private String password;
}
