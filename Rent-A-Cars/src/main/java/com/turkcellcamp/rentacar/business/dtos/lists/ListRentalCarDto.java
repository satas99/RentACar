package com.turkcellcamp.rentacar.business.dtos.lists;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentalCarDto {
	private int rentalCarId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private double totalDailyPrice;
    private double startingKilometer;
    private double returnKilometer;
	private int rentCityId;
	private int returnCityId;
	private int carId;
	private int customerId;
	
}
