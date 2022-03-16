package com.turkcellcamp.rentacar.business.dtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderedAdditionalServiceByIdDto {
    private int orderedAdditionalServiceId;
    private int additionalServiceId;
    private int rentalCarId;
}
