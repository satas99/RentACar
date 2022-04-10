package com.turkcellcamp.rentacar.business.requests.creates;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarRequest {
	@NotNull
	@FutureOrPresent
	private LocalDate rentDate;
	
	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;
	
	@Positive
	@NotNull
	private int rentCityId;
	
	@Positive
	@NotNull
	private int returnCityId;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@Positive
	private int customerId;
}
