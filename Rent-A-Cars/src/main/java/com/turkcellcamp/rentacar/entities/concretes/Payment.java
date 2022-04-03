package com.turkcellcamp.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.type.ListType;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payments")
@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private int paymentId;
	
	@Column(name = "payment_date")
	private LocalDate paymentDate;
	
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "rentalCar_id")
	private int rentalCarId;
	
	@Column(name="invoice_id")
	private int invoiceId;
	
	@Column(name = "payment_total")
	private double paymentTotal;

}
