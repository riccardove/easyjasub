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

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleLineContentToHtmlTable extends SubtitleLineContentToHtmlBase {

	private final boolean hasFurigana;
	private final boolean hasDictionary;
	private final boolean hasKanji;
	private final boolean hasRomaji;
	private final boolean hasRuby;

	public SubtitleLineContentToHtmlTable(boolean hasFurigana,
			boolean hasRomaji, boolean hasDictionary, boolean hasKanji,
			boolean hasRuby) {
		this.hasFurigana = hasFurigana;
		this.hasRomaji = hasRomaji;
		this.hasDictionary = hasDictionary;
		this.hasKanji = hasKanji;
		this.hasRuby = hasRuby;
	}

	@Override
	public void appendItems(RendersnakeHtmlCanvas html, List<SubtitleItem> items)
			throws IOException {
		html.table();
		html.newline();
		if (!hasRuby) {
			if (hasFurigana && hasKanji) {
				html.tr("top");
				for (SubtitleItem item : items) {
					html.space();
					renderFurigana(html, item);
					html.newline();
				}
				html._tr();
				html.newline();
			}
		}
		html.tr("center");
		for (SubtitleItem item : items) {
			renderOnCenter(html, item);
			html.newline();
		}
		html._tr();
		html.newline();
		if (hasRomaji) {
			html.tr("bottom");
			for (SubtitleItem item : items) {
				html.write("  ");
				renderRomaji(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		if (hasDictionary) {
			html.tr("translation");
			for (SubtitleItem item : items) {
				html.write("  ");
				renderDictionary(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		html._table();
	}

	private void renderRomaji(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.tdEmpty();
		} else {
			html.td(item.getPartOfSpeech(), romaji);
		}

	}

	private void renderDictionary(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String dict = item.getDictionary();
		if (dict != null) {
			html.td(dict);
		} else {
			html.tdEmpty();
		}
	}

	private void renderFurigana(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.tdEmpty();
		} else {
			html.td(item.getPartOfSpeech(), furigana);
		}

	}

	private void renderOnCenter(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		html.write("  ");
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (text == null) {
			html.tdEmpty();
		} else if (elements == null) {
			String styleClass = item.getPartOfSpeech();
			if (styleClass == null) {
				html.td();
			} else {
				html.tdOpen(styleClass);
			}
			appendText(html, text);
			html._td();
		} else if (hasKanji) {
			html.tdOpen(item.getPartOfSpeech());
			if (hasRuby && hasFurigana) {
				appendFuriganaElements(html, elements);
			} else {
				appendElements(html, elements);
			}
			html._td();
		} else {
			html.td(item.getPartOfSpeech(), item.getFurigana());
		}
	}

}
