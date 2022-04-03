package com.turkcellcamp.rentacar.business.requests.updates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	
	@Positive
    private int additionalServiceId;
	
	@Positive
	private int rentalCarId;
}