package com.github.riccardove.easyjasub.inputtextsub;

/*
 * #%L
 * easyjasub
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


import subtitleFile.Caption;

/**
 * Caption in an input text subtitle
 */
public class InputTextSubCaption {
	protected InputTextSubCaption(Caption caption) {
		start = new InputTextSubTime(caption.start);
		end = new InputTextSubTime(caption.end);
		content = caption.content;
	}

	private final InputTextSubTime start;
	private final InputTextSubTime end;
	private final String content;
		
	public String getIdxStartTime() {
		return "invalid"; // "0" + caption.startStr.replace('.', ':') + "0";
	}

	public InputTextSubTime getStart() {
		return start;
	}

	public InputTextSubTime getEnd() {
		return end;
	}

	public String getContent() {
		return content;
	}
}
