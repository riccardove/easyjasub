package com.github.riccardove.easyjasub;

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

import java.util.List;

public class SubtitleItem {

	private String furigana;
	private String dictionary;
	private String text;
	private String romaji;
	private String comment;
	private String partOfSpeech;
	private List<Inner> elements;
	private String baseForm;
	private String inflectionForm;
	private String inflectionType;

	public String getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(String baseForm) {
		this.baseForm = baseForm;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String grammarElement) {
		this.partOfSpeech = grammarElement;
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

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
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

	public static class Inner {
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

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (elements != null) {
			for (Inner item : elements) {
				if (item.getKanji() != null) {
					result.append(item.getKanji());
				} else {
					result.append(item.getText());
				}
			}
		}
		return result.toString();
	}

	public String getInflectionForm() {
		return inflectionForm;
	}

	public void setInflectionForm(String inflectionForm) {
		this.inflectionForm = inflectionForm;
	}

	public String getInflectionType() {
		return inflectionType;
	}

	public void setInflectionType(String inflectionType) {
		this.inflectionType = inflectionType;
	}
}
