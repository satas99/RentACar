package com.turkcellcamp.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetPaymentByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListPaymentDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreatePaymentRequest;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("/getall")
	DataResult<List<ListPaymentDto>> getAll(){
		return this.paymentService.getAll();
	}
	
	@PostMapping("/add")
	Result add(boolean rememberMe,@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
		return this.paymentService.add(rememberMe,createPaymentRequest);
	}

	@GetMapping("/getid/{paymentId}")
	DataResult<GetPaymentByIdDto> getById(@RequestParam("paymentId") int paymentId){
		return this.paymentService.getById(paymentId);
	}
	
	@DeleteMapping("/delete")
	Result delete(@RequestParam("paymentId") int id) {
		return this.paymentService.delete(id);
	}
	
}

