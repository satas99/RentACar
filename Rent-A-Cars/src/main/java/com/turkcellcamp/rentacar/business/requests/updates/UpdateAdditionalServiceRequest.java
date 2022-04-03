package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	@NotBlank
	private String serviceName;
	@NotBlank
	private double dailyPrice;
}
