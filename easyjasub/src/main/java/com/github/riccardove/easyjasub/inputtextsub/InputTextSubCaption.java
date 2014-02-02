package com.github.riccardove.easyjasub.inputtextsub;

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
