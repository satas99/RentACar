package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.entities.concretes.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
	Customer getByCustomerId(int id);
}
