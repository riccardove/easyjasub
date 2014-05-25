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

import com.github.riccardove.easyjasub.SubtitleItem.Inner;
import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleLineContentToHtmlParagraph extends SubtitleLineContentToHtmlBase {

	private final boolean hasFurigana;
	private final boolean hasDictionary;
	private final boolean hasKanji;
	private final boolean hasRomaji;

	public SubtitleLineContentToHtmlParagraph(boolean hasFurigana,
			boolean hasRomaji, boolean hasDictionary, boolean hasKanji) {
		this.hasFurigana = hasFurigana;
		this.hasRomaji = hasRomaji;
		this.hasDictionary = hasDictionary;
		this.hasKanji = hasKanji;
	}

	@Override
	public void appendItems(RendersnakeHtmlCanvas html, List<SubtitleItem> items)
			throws IOException {
		html.p();
		for (SubtitleItem item : items) {
			appendRuby(html, item);
		}
		html.newline();
		html._p();
		html.newline();
	}

	private void appendRuby(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		// if (!PartOfSpeech.symbol.toString().equals(item.getPartOfSpeech())) {
		// html.newline();
		// }
		boolean addSpacing = (item.getPartOfSpeech() != null && item
				.getPartOfSpeech().contains("particle"));
		if (addSpacing) {
			html.newline();
		}
		List<SubtitleItem.Inner> elements = item.getElements();
		if (elements == null && hasKanji) {
			// there is no kanji element in the item
			if (hasFurigana && item.getFurigana() != null) {
				// item has a valid furigana, show text with furigana
				appendRuby(html, item.getPartOfSpeech(), item.getText(),
						item.getFurigana());
			} else if (hasRomaji && item.getRomaji() != null) {
				// item has a valid romaji, show text with romaji as ruby
				appendRuby(html, item.getPartOfSpeech(), item.getText(),
						item.getRomaji());
			} else if (hasDictionary && item.getDictionary() != null) {
				// item has a valid dictionary, show text with dictionary as
				// ruby
				appendRuby(html, item.getPartOfSpeech(), item.getText(),
						item.getDictionary());
			} else {
				appendText(html, item.getText());
			}
		} else if (!hasKanji) {
			// you do not want to show kanji
			if (hasFurigana) {
				// if item has a valid furigana then we show as hiragana text
				String text = item.getFurigana();
				if (text == null) {
					// otherwise we use text
					text = item.getText();
				}
				if (hasRomaji && item.getRomaji() != null) {
					// show hiragana text with romaji as ruby
					appendRuby(html, item.getPartOfSpeech(), text,
							item.getRomaji());
				} else if (hasDictionary && item.getDictionary() != null) {
					// show hiragana text with dictionary
					appendRuby(html, item.getPartOfSpeech(), text,
							item.getDictionary());
				} else {
					// show hiragana text
					appendText(html, text);
				}
			} else {
				// we do not want to show kanji nor furigana
				if (hasRomaji) {
					// we show romaji when possible
					String text = item.getRomaji();
					if (text == null) {
						text = item.getText();
					}
					if (hasDictionary && item.getDictionary() != null) {
						appendRuby(html, item.getPartOfSpeech(), text,
								item.getDictionary());
					} else {
						appendText(html, text);
					}
				} else {
					if (hasDictionary && item.getDictionary() != null) {
						appendRuby(html, item.getPartOfSpeech(),
								item.getText(), item.getDictionary());
					} else {
						appendText(html, item.getText());
					}
				}
			}
		} else if (hasFurigana || hasRomaji || hasDictionary) {
			if (hasFurigana) {
				if (allKanjiElementsHaveFurigana(elements)) {
					appendFuriganaElements(html, elements,
							item.getPartOfSpeech());
				} else {
					html.ruby(item.getPartOfSpeech());
					appendElements(html, elements);
					html.rt(item.getFurigana());
					html._ruby();
				}
			} else if (hasRomaji) {
				html.ruby(item.getPartOfSpeech());
				appendElements(html, elements);
				html.rt(item.getRomaji());
				html._ruby();
			} else {
				html.ruby(item.getPartOfSpeech());
				appendElements(html, elements);
				html.rt(item.getDictionary());
				html._ruby();
			}
		} else {
			html.span(item.getPartOfSpeech());
			appendElements(html, elements);
			html._span();
		}
		if (addSpacing) {
			html.newline();
		}
	}

	private boolean allKanjiElementsHaveFurigana(List<Inner> elements) {
		for (Inner element : elements) {
			if (element.getKanji() != null && element.getText() == null) {
				return false;
			}
		}
		return true;
	}

	private void appendRuby(RendersnakeHtmlCanvas html, String grammar,
			String text, String ruby) throws IOException {
		html.ruby(grammar);
		html.write(text);
		html.rt(ruby);
		html._ruby();
	}

}
