package com.turkcellcamp.rentacar.core.utilities.results;

public class SuccessResult extends Result {
	public SuccessResult() {
		super(true);//
	}

	public SuccessResult(String message) {
		super(true, message);//extend ettiği classtaki constructoru çalıştırır
	}
}
