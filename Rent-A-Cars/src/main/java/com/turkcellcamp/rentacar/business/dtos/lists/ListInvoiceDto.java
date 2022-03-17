package com.turkcellcamp.rentacar.business.dtos.lists;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListInvoiceDto {
	
	private int invoiceId;

	private String invoiceNo;

	private LocalDate createDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private int numberDays;

	private double rentTotalPrice;

	private int customerId;

	private int rentalCarId;
}
