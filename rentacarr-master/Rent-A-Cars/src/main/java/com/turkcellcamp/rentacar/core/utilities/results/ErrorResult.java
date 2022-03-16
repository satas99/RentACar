package com.turkcellcamp.rentacar.core.utilities.results;

public class ErrorResult extends Result{
	public ErrorResult() {
		super(false);//
	}

	public ErrorResult(String message) {
		super(false, message);//extend ettiği classtaki constructoru çalıştırır
	}
}
