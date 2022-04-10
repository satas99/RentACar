package com.turkcellcamp.rentacar.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {
	@FutureOrPresent
	private LocalDate returnDate;

	@Positive
	private int returnCityId;

	@Positive
	private double returnKilometer;

}
