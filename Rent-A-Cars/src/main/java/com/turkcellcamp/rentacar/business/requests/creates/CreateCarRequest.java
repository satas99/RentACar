package com.turkcellcamp.rentacar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	private double dailyPrice;
	private int modelYear;
	private String description;
	private double kilometerInfo;
	private int brandId;
	private int colorId;
}
