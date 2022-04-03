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
public class CreateIndividualCustomerRequest {
	
	@NotBlank
	@Size(min=2)
	private String firstName;
	
	@NotBlank
	@Size(min=2)
	private String lastName;
	
	private String identityNumber;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(min=6)
	private String password;
}
