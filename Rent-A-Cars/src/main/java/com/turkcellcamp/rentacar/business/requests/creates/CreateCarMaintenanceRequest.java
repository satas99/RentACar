package com.turkcellcamp.rentacar.business.requests.creates;




import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	
	@NotBlank
	@Size(min=2,max=200)
	private String description;
	
	@NotBlank
	@Positive
	private int carId;
	

}
