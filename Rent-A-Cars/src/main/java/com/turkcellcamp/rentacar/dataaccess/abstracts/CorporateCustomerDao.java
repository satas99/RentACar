package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.entities.concretes.CorporateCustomer;


public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
	CorporateCustomer getByCorporateCustomerId(int id);
	CorporateCustomer findByEmail(String mail);
	CorporateCustomer findByTaxNumber(String taxNumber);
}


