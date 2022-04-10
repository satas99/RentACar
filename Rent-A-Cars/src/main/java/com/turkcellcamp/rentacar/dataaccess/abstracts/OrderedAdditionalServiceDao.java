package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {
	OrderedAdditionalService getByOrderedAdditionalServiceId(int id);

	List<OrderedAdditionalService> getByRentalCar_rentalCarId(int id);

	List<OrderedAdditionalService> getByAdditionalService_AdditionalServiceId(int id);
}
