package com.turkcellcamp.rentacar.business.dtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	private int corporateCustomerId;
	private String companyName;
	private String taxNumber;
	private String email;
	private String password;
}
