package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	@Positive
	private double dailyPrice;
	
	@Positive
	private int modelYear;
	
	@NotBlank
	private String description;
	
	@NotBlank
	@Positive
	private double kilometerInfo;
	
	@NotBlank
	@Positive
	private int brandId;
	
	@NotBlank
	@Positive
	private int colorId;
}
