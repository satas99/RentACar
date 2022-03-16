package com.turkcellcamp.rentacar.business.requests.creates;

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
public class CreateInvoiceRequest {

	private long invoiceNo;

	private LocalDate createDate;
	
	private int customerId;
	
	private int rentalCarId;

}
