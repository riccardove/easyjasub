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


import java.io.IOException;
import java.util.List;

import com.github.riccardove.easyjasub.commons.CommonsLangStringUtils;
import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

abstract class SubtitleLineContentToHtmlBase {

	public abstract void appendItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException;

	protected void appendElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.spanKanji(kanji);
			} else {
				html.write(element.getText());
			}
		}
	}

	protected void appendText(RendersnakeHtmlCanvas html, String text)
			throws IOException {
		String trimmedText = normalizeText(text.replace('　', ' ').trim());
		html.write(trimmedText);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;");
		}
	}

	// TODO: write furigana separately on each kanji section
	private void appendRubyElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.ruby();
				html.spanKanji(kanji);
				html.rt(element.getText());
				html._ruby();
			} else {
				html.write(element.getText());
			}
		}
	}

	private String normalizeText(String text) {
		return CommonsLangStringUtils
				.replaceChars(
						text,
						"０１２３４５６７８９ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ，．：；？！´｀¨＾～＜＞",
						"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.:;?!'`¨＾~<>");
	}

	private boolean isSymbolicPartOfSpeech(String pos) {
		return pos != null && ("noun-numeric".equals(pos));
	}

}
