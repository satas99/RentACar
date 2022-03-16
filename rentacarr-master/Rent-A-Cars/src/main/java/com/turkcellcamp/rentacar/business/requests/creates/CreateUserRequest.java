package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

	@Email
	@NotNull
	private String email;
	
	@NotNull
	@Size(min=6)
	private String password;

}
