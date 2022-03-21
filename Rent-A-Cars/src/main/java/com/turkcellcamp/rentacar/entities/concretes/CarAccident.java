package com.turkcellcamp.rentacar.entities.concretes;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="car_accidents")
@Entity
public class CarAccident {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_accident_id")
	private int carAccidentId;
	
	@Column(name="car_accident_description")
	private String carAccidentDescription;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;

}