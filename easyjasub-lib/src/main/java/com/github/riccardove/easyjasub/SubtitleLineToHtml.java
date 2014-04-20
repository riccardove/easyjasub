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
			if (isSingleLine) {
				appendRubyItems(html, items);
			} else {
				appendItems(html, items);
			}
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

	private void appendRubyItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
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
		if (!PartOfSpeech.symbol.toString().equals(item.getGrammarElement())) {
			html.newline();
		}
		List<SubtitleItem.Inner> elements = item.getElements();
		if (elements == null && hasKanji) {
			// there is no kanji element in the item
			if (hasFurigana && item.getFurigana() != null) {
				// item has a valid furigana, show text with furigana
				appendRuby(html, item.getGrammarElement(), item.getText(),
						item.getFurigana());
			} else if (hasRomaji && item.getRomaji() != null) {
				// item has a valid romaji, show text with romaji as ruby
				appendRuby(html, item.getGrammarElement(), item.getText(),
						item.getRomaji());
			} else if (hasDictionary && item.getDictionary() != null) {
				// item has a valid dictionary, show text with dictionary as
				// ruby
				appendRuby(html, item.getGrammarElement(), item.getText(),
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
					appendRuby(html, item.getGrammarElement(), text,
							item.getRomaji());
				} else if (hasDictionary && item.getDictionary() != null) {
					// show hiragana text with dictionary
					appendRuby(html, item.getGrammarElement(), text,
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
						appendRuby(html, item.getGrammarElement(), text,
								item.getDictionary());
					} else {
						appendText(html, text);
					}
				} else {
					if (hasDictionary && item.getDictionary() != null) {
						appendRuby(html, item.getGrammarElement(),
								item.getText(), item.getDictionary());
					} else {
						appendText(html, item.getText());
					}
				}
			}
		} else {
			html.ruby(item.getGrammarElement());
			if (hasFurigana) {
				// TODO: sometimes furigana may be limited to some kanji part of
				// the word
				appendElements(html, elements);
				html.rt(item.getFurigana());
			} else if (hasRomaji) {
				appendElements(html, elements);
				html.rt(item.getRomaji());
			} else if (hasDictionary) {
				appendElements(html, elements);
				html.rt(item.getDictionary());
			}
			html._ruby();
		}
	}

	private void appendRuby(RendersnakeHtmlCanvas html, String grammar,
			String text, String ruby) throws IOException {
		html.ruby(grammar);
		html.write(text);
		html.rt(ruby);
		html._ruby();
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

	private void appendTranslation(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		String translation = line.getTranslation();
		if (translation != null) {
			html.newline();
			html.p(translation);
			html.newline();
		}
	}

	private void appendSubText(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		html.table("center", line.getSubText());
		html.newline();
	}

	private void appendItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
		html.table();
		html.newline();
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
		html.tr("center");
		for (SubtitleItem item : items) {
			renderOnCenter(html, item, hasKanji);
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

	private void renderFurigana(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.tdEmpty();
		} else {
			html.td(item.getGrammarElement(), furigana);
		}

	}

	private void renderOnCenter(RendersnakeHtmlCanvas html, SubtitleItem item,
			boolean showKanji) throws IOException {
		html.write("  ");
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (text == null) {
			html.tdEmpty();
		} else if (elements == null) {
			String styleClass = item.getGrammarElement();
			if (styleClass == null) {
				html.td();
			} else {
				html.tdOpen(styleClass);
			}
			appendText(html, text);
			html._td();
		} else if (showKanji) {
			html.tdOpen(item.getGrammarElement());
			appendElements(html, elements);
			html._td();
		} else {
			html.td(item.getGrammarElement(), item.getFurigana());
		}
	}

	private void appendText(RendersnakeHtmlCanvas html, String text)
			throws IOException {
		String trimmedText = text.replace('　', ' ').trim();
		html.write(trimmedText);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;");
		}
	}

	private void appendRubyElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.ruby();
				html.span(kanji);
				html.rt(element.getText());
				html._ruby();
			} else {
				html.write(element.getText());
			}
		}
	}

	private void appendElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.span(kanji);
			} else {
				html.write(element.getText());
			}
		}
	}

	private void renderRomaji(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.tdEmpty();
		} else {
			html.td(item.getGrammarElement(), romaji);
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

}
