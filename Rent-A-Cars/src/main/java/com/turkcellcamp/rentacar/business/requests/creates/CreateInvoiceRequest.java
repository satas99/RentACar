package com.turkcellcamp.rentacar.business.requests.creates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotBlank
	private String invoiceNo;
	
	@NotBlank
	@Positive
	private int customerId;
	
	@NotBlank
	@Positive
	private int rentalCarId;

}
