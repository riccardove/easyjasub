package com.github.riccardove.easyjasub;

public class WkhtmltoimageException  extends Exception {

	private static final long serialVersionUID = 3985115675407981907L;

	protected WkhtmltoimageException(String message, Exception ex) {
		super(message, ex);
	}

	protected WkhtmltoimageException(String message) {
		super(message);
	}
}
