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
	@NotBlank
	@FutureOrPresent
	private LocalDate rentDate;
	
	@NotBlank
	@FutureOrPresent
	private LocalDate returnDate;
	
	@Positive
	@NotBlank
	private int rentCityId;
	
	@Positive
	@NotBlank
	private int returnCityId;
	
	@NotBlank
	@Positive
	private int carId;
	
	@NotBlank
	@Positive
	private int customerId;
}
