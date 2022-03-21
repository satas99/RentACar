package com.turkcellcamp.rentacar.entities.concretes;

import java.util.List;import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="colors")
@Entity
public class Color {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="color_id")
	private int colorId;
	
	@Column(name="color_name")
	private String colorName;
	
	@OneToMany(mappedBy="color")
	private List<Car> cars;
}
