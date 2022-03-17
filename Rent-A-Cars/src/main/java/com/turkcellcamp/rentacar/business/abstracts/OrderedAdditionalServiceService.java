package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
	
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

	Result delete(int id);

	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();
	
	Result update(int id,UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);

	DataResult<GetOrderedAdditionalServiceByIdDto> getByOrderedAdditionalServiceId(int orderedadditionalServiceId);
	
	DataResult<List<ListOrderedAdditionalServiceDto>> getOrderedAdditionalServiceByRentalCarId(int rentalCarId);
}
