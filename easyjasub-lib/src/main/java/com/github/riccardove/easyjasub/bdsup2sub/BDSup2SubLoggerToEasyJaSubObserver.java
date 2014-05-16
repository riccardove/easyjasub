package com.github.riccardove.easyjasub.bdsup2sub;

import bdsup2sub.core.LibLoggerObserver;

import com.github.riccardove.easyjasub.EasyJaSubObserver;

class BDSup2SubLoggerToEasyJaSubObserver implements LibLoggerObserver {

	private final EasyJaSubObserver observer;

	public BDSup2SubLoggerToEasyJaSubObserver(EasyJaSubObserver observer) {
		this.observer = observer;

	}

	@Override
	public void error(String message) {
		observer.onSupConvertMessage("Error! " + message.trim());
	}

	@Override
	public void info(String message) {
		observer.onSupConvertMessage(message.trim());
	}

	@Override
	public void trace(String message) {
		observer.onSupConvertMessage("trace: " + message.trim());
	}

	@Override
	public void warn(String message) {
		observer.onSupConvertMessage("Warning! " + message.trim());
	}

}
