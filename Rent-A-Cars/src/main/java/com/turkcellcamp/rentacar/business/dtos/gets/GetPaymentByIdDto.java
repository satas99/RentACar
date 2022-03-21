package com.turkcellcamp.rentacar.business.dtos.gets;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentByIdDto {
	private int paymentId;
	private int invoiceId;
	private int orderedAdditionalServiceId;
}
