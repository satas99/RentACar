package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	private double dailyPrice;
	private String description;
	private double kilometerInfo;

}
