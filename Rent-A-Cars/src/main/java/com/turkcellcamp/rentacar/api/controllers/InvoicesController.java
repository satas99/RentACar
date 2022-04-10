package com.turkcellcamp.rentacar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListInvoiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;



@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	private InvoiceService invoiceService;
	
	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
//	@PostMapping("/add")
//	Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest){
//		return this.invoiceService.add(createInvoiceRequest);
//	}
	@DeleteMapping("/delete")
	Result delete(@RequestParam("invoiceId") int id){
		return this.invoiceService.delete(id);
	}
	@GetMapping("/getall")
	DataResult<List<ListInvoiceDto>> getAll(){
		return this.invoiceService.getAll();
	}
	@PutMapping("/update")
	Result update(@RequestParam("invoiceId") int id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
		return this.invoiceService.update(id, updateInvoiceRequest);
	}
	@GetMapping("/getid")
	DataResult<GetInvoiceByIdDto> getByInvoiceId(@RequestParam("invoiceId") int invoiceId){
		return this.invoiceService.getById(invoiceId);
	}
	@GetMapping("/getbydate")
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate){
		return this.invoiceService.getByDateOfBetween(startDate, finishDate);
	}
	@GetMapping("/getInvoicesByCustomerId")
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(@RequestParam("customerId") int id) {
		return this.invoiceService.getInvoiceByCustomer(id);
	}
	
	@GetMapping("/getInvoicesByRentalCarId")
	public DataResult<List<ListInvoiceDto>> getInvoiceByRentalCar(@RequestParam("rentalCarId") int id){
		return this.invoiceService.getInvoiceByRentalCar(id);
	}
}
