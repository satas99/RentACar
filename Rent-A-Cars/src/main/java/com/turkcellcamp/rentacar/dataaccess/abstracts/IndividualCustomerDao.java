package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
	IndividualCustomer getByIndividualCustomerId(int id);
	IndividualCustomer findByEmail(String mail);
	IndividualCustomer findByIdentityNumber(String identityNmuber);
}
