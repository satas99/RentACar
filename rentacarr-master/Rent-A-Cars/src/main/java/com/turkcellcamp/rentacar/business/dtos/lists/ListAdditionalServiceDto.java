package com.turkcellcamp.rentacar.business.dtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAdditionalServiceDto {
	private int id;
	private String serviceName;
	private double dailyPrice;
}
