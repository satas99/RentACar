package com.turkcellcamp.rentacar.business.dtos.gets;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceByIdDto {
	private int carMaintenanceId;
	private String description;
	private LocalDate returnDate;
	private int carId;
}
