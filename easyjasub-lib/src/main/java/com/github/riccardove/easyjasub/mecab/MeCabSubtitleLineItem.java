package com.github.riccardove.easyjasub.mecab;

class MeCabSubtitleLineItem {

	private String reading;
	private String text;
	private String grammarElement;

	public String getGrammarElement() {
		return grammarElement;
	}

	public void setGrammarElement(String grammarElement) {
		this.grammarElement = grammarElement;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
