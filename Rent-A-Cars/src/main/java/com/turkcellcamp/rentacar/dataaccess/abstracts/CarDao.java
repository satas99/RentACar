package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.Car;


@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	Car getByCarId(int id);

	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);

	List <Car> getByBrand_BrandId(int id);
	
	List <Car> getByColor_ColorId(int id);
}
