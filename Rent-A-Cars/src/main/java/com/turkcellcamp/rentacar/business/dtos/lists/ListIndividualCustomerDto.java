package com.turkcellcamp.rentacar.business.dtos.lists;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListIndividualCustomerDto {
	private String firstName;
	private String lastName;
	private String identityNumber;
	private String email;
	private String password;
}
