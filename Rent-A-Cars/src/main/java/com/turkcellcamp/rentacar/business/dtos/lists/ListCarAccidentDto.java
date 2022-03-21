package com.turkcellcamp.rentacar.business.dtos.lists;

import com.turkcellcamp.rentacar.business.dtos.gets.GetCarAccidentByIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarAccidentDto {
	
	private int carAccidentId;
	
	private String carAccidentDescription;

	private int carId;
}
