package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	@NotBlank
	private String serviceName;
	
	@Positive
	private double dailyPrice;
}
