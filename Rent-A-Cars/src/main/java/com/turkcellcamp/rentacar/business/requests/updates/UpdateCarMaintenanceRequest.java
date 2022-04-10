package com.turkcellcamp.rentacar.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	@NotBlank
	@Size(min=1,max=200)
	private String description;
	
	@NotNull
	private LocalDate returnDate;

}
