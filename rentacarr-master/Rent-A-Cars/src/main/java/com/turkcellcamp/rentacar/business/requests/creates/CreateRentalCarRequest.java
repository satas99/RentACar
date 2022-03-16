package com.turkcellcamp.rentacar.business.requests.creates;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarRequest {
	@NotNull
	private LocalDate rentDate;
	@NotNull
	private LocalDate returnDate;
	private int rentCityId;
	private int returnCityId;
	@NotNull
	private int carId;
	@NotNull
	private int customerId;
}
