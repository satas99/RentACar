package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.entities.concretes.CarAccident;

public interface CarAccidentDao extends JpaRepository<CarAccident, Integer> {
	public CarAccident getByCarAccidentId(int carAccidentId);

	public List<CarAccident> getByCar_CarId(int carId);
}
