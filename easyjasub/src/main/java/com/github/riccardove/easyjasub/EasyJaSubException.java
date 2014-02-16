package com.github.riccardove.easyjasub;

public class EasyJaSubException extends Exception {

	public EasyJaSubException(String message, Exception ex) {
		super(message, ex);
	}

	public EasyJaSubException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 6787324183180022282L;
}
