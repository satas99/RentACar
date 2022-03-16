package com.turkcellcamp.rentacar.business.dtos.gets;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRentalCarByIdDto {
	private int rentalCarId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private double totalDailyPrice;
	private int rentCityId;
	private int returnCityId;
	private int carId;
	private int customerId;
}
