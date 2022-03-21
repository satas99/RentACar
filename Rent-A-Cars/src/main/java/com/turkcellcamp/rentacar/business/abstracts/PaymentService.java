package com.turkcellcamp.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetPaymentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListPaymentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

public interface PaymentService {
	
	DataResult<List<ListPaymentDto>> getAll();

	Result isBankAdd(CreatePaymentRequest createPaymentRequest);
	
	Result halkBankAdd(CreatePaymentRequest createPaymentRequest);

	DataResult<GetPaymentByIdDto> getById(int paymentId);

	Result delete(int id);
}
