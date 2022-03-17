package com.turkcellcamp.rentacar.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {
	@NotNull
	private LocalDate returnDate;
	private int returnCityId;
	private double returnKilometer;

}
