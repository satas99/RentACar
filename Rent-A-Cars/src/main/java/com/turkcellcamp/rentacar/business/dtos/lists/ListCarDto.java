package com.turkcellcamp.rentacar.business.dtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCarDto {
	private int carId;
	private String brandName;
	private String colorName;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private double kilometerInfo;


}
