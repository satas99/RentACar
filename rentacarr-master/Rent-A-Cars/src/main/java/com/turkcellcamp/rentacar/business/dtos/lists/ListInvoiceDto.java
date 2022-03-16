package com.turkcellcamp.rentacar.business.dtos.lists;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListInvoiceDto {
	
	private int invoiceId;

	private long invoiceNo;

	private LocalDate createDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private int numberDays;

	private double rentTotalPrice;

	private int customerId;

	private int rentalCarId;
}
