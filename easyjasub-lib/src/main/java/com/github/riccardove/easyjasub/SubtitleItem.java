package com.github.riccardove.easyjasub;

import java.util.List;

public class SubtitleItem {

	private String furigana;
	private String translation;
	private String text;
	private String romaji;
	private String comment;
	private String grammarElement;
	private List<Inner> elements;
	
	public String getGrammarElement() {
		return grammarElement;
	}
	public void setGrammarElement(String grammarElement) {
		this.grammarElement = grammarElement;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFurigana() {
		return furigana;
	}
	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRomaji() {
		return romaji;
	}
	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}
	
	public List<Inner> getElements() {
		return elements;
	}
	public void setElements(List<Inner> elements) {
		this.elements = elements;
	}

	public static class Inner 
	{
		private String kanji;
		public String getKanji() {
			return kanji;
		}
		public void setKanji(String kanji) {
			this.kanji = kanji;
		}
		private String text;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
}
