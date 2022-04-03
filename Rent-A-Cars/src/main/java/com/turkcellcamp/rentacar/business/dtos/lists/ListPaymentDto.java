package com.turkcellcamp.rentacar.business.dtos.lists;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPaymentDto {
	private int paymentId;
	private LocalDate paymentDate;
	private int customerId;
	private int rentalCarId;
	private int invoiceId;
	private double paymentTotal;
}
