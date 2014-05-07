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
import java.util.Iterator;
import java.util.List;

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleLineToHtml {

	private final boolean isSingleLine;
	private final boolean hasFurigana;
	private final boolean hasDictionary;
	private final boolean hasKanji;
	private final boolean hasRomaji;
	private final boolean showTranslation;

	public SubtitleLineToHtml(boolean isSingleLine, boolean hasFurigana,
			boolean hasRomaji, boolean hasDictionary, boolean hasKanji,
			boolean showTranslation) {
		this.isSingleLine = isSingleLine;
		this.hasFurigana = hasFurigana;
		this.hasRomaji = hasRomaji;
		this.hasDictionary = hasDictionary;
		this.hasKanji = hasKanji;
		this.showTranslation = showTranslation;
	}

	public String toHtml(SubtitleLine s, String cssFileRef) throws IOException {
		RendersnakeHtmlCanvas html = new RendersnakeHtmlCanvas(
				EasyJaSubWriter.Newline);
		html.header(cssFileRef, EasyJaSubCharset.CHARSETSTR);
		html.newline();

		List<SubtitleItem> items = s.getItems();
		if (items != null) {
			SubtitleLineContentToHtmlBase itemsWriter;
			if (isSingleLine) {
				itemsWriter = new SubtitleLineContentToHtmlParagraph(
						hasFurigana, hasRomaji, hasDictionary, hasKanji);
			} else {
				itemsWriter = new SubtitleLineContentToHtmlTable(hasFurigana,
						hasRomaji, hasDictionary, hasKanji);
			}
			itemsWriter.appendItems(html, items);
		} else {
			if (s.getSubText() != null) {
				appendSubText(html, s);
			}
		}
		if (showTranslation) {
			appendTranslation(html, s);
		}
		if (items != null) {
			appendComment(html, items);
		}

		html.footer();

		return html.toString();
	}

	private void appendComment(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
		boolean hasComment = false;
		for (SubtitleItem item : items) {
			String comment = item.getComment();
			if (comment != null) {
				if (!hasComment) {
					hasComment = true;
					html.comment();
				}
				html.write(comment);
				html.newline();
			}
		}
		if (hasComment) {
			html._comment();
		}
	}

	private static String getTranslationStr(
			List<SubtitleTranslatedLine> translation) {
		Iterator<SubtitleTranslatedLine> it = translation.iterator();
		String first = it.next().getText();
		if (!it.hasNext()) {
			return first;
		}
		StringBuilder text = new StringBuilder(first);
		do {
			text.append(BreakStr);
			text.append(it.next().getText());
		} while (it.hasNext());
		return text.toString();
	}

	private static final String BreakStr = "  ";

	private void appendTranslation(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		List<SubtitleTranslatedLine> translation = line.getTranslation();
		if (translation != null && translation.size() > 0) {
			html.newline();
			html.p(getTranslationStr(translation));
			html.newline();
		}
	}

	private void appendSubText(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		html.table("center", line.getSubText());
		html.newline();
	}

}
