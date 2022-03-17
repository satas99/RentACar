package com.turkcellcamp.rentacar.business.dtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCorporateCustomerByIdDto {
	private int corporateCustomerId;
	private String companyName;
	private String taxNumber;
	private String email;
	private String password;
}
