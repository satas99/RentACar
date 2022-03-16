package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.CorporateCustomer;


public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
}
