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

public interface InvoiceService {
	
	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;

	DataResult<List<ListInvoiceDto>> getAll();
	
	Result update(int id,UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;

	DataResult<GetInvoiceByIdDto> getByInvoiceId(int invoiceId) throws BusinessException;
	
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id);
}
