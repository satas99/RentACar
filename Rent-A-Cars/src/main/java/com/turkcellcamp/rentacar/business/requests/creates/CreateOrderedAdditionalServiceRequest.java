package com.turkcellcamp.rentacar.business.requests.creates;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	@Positive
    private int AdditionalServiceId;
	
	@NotNull
	@Positive
    private int rentalCarId;
}
