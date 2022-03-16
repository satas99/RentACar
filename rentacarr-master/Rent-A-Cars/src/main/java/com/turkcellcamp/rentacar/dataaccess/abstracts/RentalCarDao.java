package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.business.dtos.lists.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

@Repository
public interface RentalCarDao extends JpaRepository<RentalCar, Integer> {
	RentalCar getByRentalCarId(int id);

	List<RentalCar> getByCar_carId(int carId);
	
}
