package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarAccidentRequest {
	@NotBlank
	private String carAccidentDescription;
}
