package com.turkcellcamp.rentacar.business.dtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPaymentDto {
	private int paymentId;
	private int invoiceId;
	private int orderedAdditionalServiceId;
}
