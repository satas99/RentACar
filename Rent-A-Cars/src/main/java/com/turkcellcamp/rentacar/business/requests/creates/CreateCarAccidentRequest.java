package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarAccidentRequest {
	
	@NotNull
	private String carAccidentDescription;

	@NotNull
	private int carId;
}
