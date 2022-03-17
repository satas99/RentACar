package com.turkcellcamp.rentacar.business.dtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndividualCustomerByIdDto {
	private int individualCustomerId;
	private String firstName;
	private String lastName;
	private String identityNumber;
	private String email;
	private String password;
}
