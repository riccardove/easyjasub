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


class EasyJaSubCssTemplateParameter {

	private String kanaFont;

	public String getKanaFont() {
		return kanaFont;
	}

	public void setKanaFont(String kanaFont) {
		this.kanaFont = kanaFont;
	}

	public String getKanjiFont() {
		return kanjiFont;
	}

	public void setKanjiFont(String kanjiFont) {
		this.kanjiFont = kanjiFont;
	}

	public String getTranslationFont() {
		return translationFont;
	}

	public void setTranslationFont(String translationFont) {
		this.translationFont = translationFont;
	}

	public int getShadow() {
		return shadow;
	}

	public void setShadow(int shadow) {
		this.shadow = shadow;
	}

	public int getKanjiSize() {
		return kanjiSize;
	}

	public void setKanjiSize(int kanjiSize) {
		this.kanjiSize = kanjiSize;
	}

	public int getHiraganaSize() {
		return hiraganaSize;
	}

	public void setHiraganaSize(int hiraganaSize) {
		this.hiraganaSize = hiraganaSize;
	}

	public int getDictionarySize() {
		return dictionarySize;
	}

	public void setDictionarySize(int dictionarySize) {
		this.dictionarySize = dictionarySize;
	}

	public int getFuriganaSize() {
		return furiganaSize;
	}

	public void setFuriganaSize(int furiganaSize) {
		this.furiganaSize = furiganaSize;
	}

	public int getTranslation() {
		return translation;
	}

	public void setTranslation(int translation) {
		this.translation = translation;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}

	public int getKanjiSpacing() {
		return kanjiSpacing;
	}

	public void setKanjiSpacing(int kanjiSpacing) {
		this.kanjiSpacing = kanjiSpacing;
	}

	public int getHiraganaSpacing() {
		return hiraganaSpacing;
	}

	public void setHiraganaSpacing(int hiraganaSpacing) {
		this.hiraganaSpacing = hiraganaSpacing;
	}

	private String kanjiFont;
	private String translationFont;
	private int shadow;
	private int kanjiSize;
	private int hiraganaSize;
	private int dictionarySize;
	private int furiganaSize;
	private int translation;
	private int lineHeight;
	private int kanjiSpacing;
	private int hiraganaSpacing;

}
