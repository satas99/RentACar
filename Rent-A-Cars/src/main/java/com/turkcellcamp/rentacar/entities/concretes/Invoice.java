package com.turkcellcamp.rentacar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
@Entity
public class Invoice {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "invoice_id")
	private int invoiceId;
	
	@Column(name= "invoice_no")
	private String invoiceNo;
	
	@Column(name= "create_date")
	private LocalDate createDate;
	
	@Column(name= "rent_date")
	private LocalDate rentDate;
	
	@Column(name= "return_date")
	private LocalDate returnDate;
	
	@Column(name= "number_days")
	private int numberDays;
	
	@Column(name = "rent_total_price")
	private double rentTotalPrice;
	
	@ManyToOne
	@JoinColumn(name= "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name ="rental_car_id")
	private RentalCar rentalCar;
	
	@OneToOne(mappedBy = "invoice")
	private Payment payment;
}
