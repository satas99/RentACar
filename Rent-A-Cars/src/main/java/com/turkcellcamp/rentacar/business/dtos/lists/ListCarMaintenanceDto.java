package com.turkcellcamp.rentacar.business.dtos.lists;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarMaintenanceDto {
	private int carMaintenanceId;
	private String description;
	private LocalDate returnDate;
	private int carId;
}
