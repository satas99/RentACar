package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {
	CarMaintenance getByCarMaintenanceId(int id);

	List<CarMaintenance> getByCar_carId(int id);
}
