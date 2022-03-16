package com.turkcellcamp.rentacar.entities.concretes;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="car_maintenances")
public class CarMaintenance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_maintenance_id")
	private int carMaintenanceId;
	
	
	@Column(name="car_maintenance_description")
	private String description;
	
	
	@Column(name="car_maintenance_returndate")
	private LocalDate returnDate; 

	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
}
