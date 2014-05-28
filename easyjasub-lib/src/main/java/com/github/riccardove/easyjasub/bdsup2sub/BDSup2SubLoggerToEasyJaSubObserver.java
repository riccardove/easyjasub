package com.github.riccardove.easyjasub.bdsup2sub;

/*
 * #%L
 * easyjasub-lib
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
