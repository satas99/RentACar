package com.turkcellcamp.rentacar.business.dtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarAccidentByIdDto {
	
	private int carAccidentId;
	
	private String carAccidentDescription;

	private int carId;
}
