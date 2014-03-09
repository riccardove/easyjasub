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


import java.util.ArrayList;
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

	public void setElements(String text) {
		ArrayList<SubtitleItem.Inner> list = new ArrayList<SubtitleItem.Inner>();
		String kanjiChars = null;
		String chars = null;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!JapaneseChar.isSmallSizeJapaneseChar(c)) {
				if (chars != null) {
					addText(list, chars);
					chars = null;
				}
				if (kanjiChars == null) {
					kanjiChars = Character.toString(c);
				} else {
					kanjiChars += c;
				}
			} else {
				if (kanjiChars != null) {
					addKanji(list, kanjiChars);
					kanjiChars = null;
				}
				if (chars == null) {
					chars = Character.toString(c);
				} else {
					chars += c;
				}
			}
		}
		if (kanjiChars != null) {
			addKanji(list, kanjiChars);
		}
		if (chars != null) {
			addText(list, chars);
		}
		elements = list;
	}

	private void addText(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setText(chars);
		list.add(inner);
	}

	private void addKanji(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setKanji(chars);
		list.add(inner);
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
