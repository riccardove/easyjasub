package com.github.riccardove.easyjasub;

public class EasyJaSubStringBuilder {

	public EasyJaSubStringBuilder() {
		text = new StringBuilder();
	}

	private final StringBuilder text;

	public void appendLine(String text) {
		this.text.append(text);
		this.text.append(SystemProperty.getLineSeparator());
	}

	@Override
	public String toString() {
		return text.toString();
	}
}
