package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
	
	Payment getByPaymentId(int paymentId);

}
