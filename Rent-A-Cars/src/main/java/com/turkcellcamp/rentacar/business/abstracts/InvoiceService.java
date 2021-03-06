package com.turkcellcamp.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcellcamp.rentacar.business.dtos.gets.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListInvoiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;

public interface InvoiceService {
	
	Invoice addForIndividualCustomer(CreateInvoiceRequest createInvoiceRequest);
	
	Invoice addForCorporateCustomer(CreateInvoiceRequest createInvoiceRequest);

	Result delete(int id);

	DataResult<List<ListInvoiceDto>> getAll();
	
	Result update(int id,UpdateInvoiceRequest updateInvoiceRequest);

	DataResult<GetInvoiceByIdDto> getById(int invoiceId);
	
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id);

	DataResult<List<ListInvoiceDto>> getInvoiceByRentalCar(int id);
}
