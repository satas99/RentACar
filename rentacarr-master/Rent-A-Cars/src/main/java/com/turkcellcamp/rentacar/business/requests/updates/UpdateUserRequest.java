package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	
	@Email
	private String email;
	
	@Size(min=6,max=15)
	private String password;
}
