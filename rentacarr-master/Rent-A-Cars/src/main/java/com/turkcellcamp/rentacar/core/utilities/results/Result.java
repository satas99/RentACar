package com.turkcellcamp.rentacar.core.utilities.results;

public class Result {
	private boolean success;;//başarılı mı değil mi ? işlem
	private String message;//bilgilendirmek
	
	public Result(boolean success) {
		this.success = success;
	}
	
	public Result(boolean success, String message) {
		this(success);//success parametresi olan constructor çalıştırır
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
