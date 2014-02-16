package com.github.riccardove.easyjasub.inputtextsub;

public class InputTextSubException extends Exception {

	private static final long serialVersionUID = -8507122335982337999L;

	protected InputTextSubException(String message, Exception ex) {
		super(message, ex);
	}

	protected InputTextSubException(String message) {
		super(message);
	}
}
