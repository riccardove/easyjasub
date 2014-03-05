package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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

import static org.rendersnake.HtmlAttributesFactory.charset;
import static org.rendersnake.HtmlAttributesFactory.class_;
import static org.rendersnake.HtmlAttributesFactory.lang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rendersnake.HtmlCanvas;

class SubtitleListHtmlFilesWriter {
	private final File cssFile;
	private final EasyJaSubObserver observer;
	private final File htmlDirectory;

	public SubtitleListHtmlFilesWriter(File htmlDirectory, File cssFile,
			EasyJaSubObserver observer) {
		this.htmlDirectory = htmlDirectory;
		this.cssFile = cssFile;
		this.observer = observer;
	}

	public void writeHtmls(SubtitleList s, EasyJaSubInput command)
			throws IOException {
		// TODO construct relative url
		String cssFileUrl = cssFile != null ? cssFile.toURI().toString()
				: "default.css";

		ArrayList<EasyJaSubWriter> writers = new ArrayList<EasyJaSubWriter>(
				s.size());
		for (SubtitleLine l : s) {
			File file = l.getHtmlFile();
			if (!file.exists()) {
				observer.onWriteHtmlFile(file);

				String htmlStr = toHtml(l, cssFileUrl, command);

				writers.add(toFile(htmlStr, file));
			} else {
				observer.onWriteHtmlFileSkipped(file);
			}
			if (writers.size() > 30) {
				closeWriters(writers);
			}
		}
		closeWriters(writers);
	}

	private void closeWriters(ArrayList<EasyJaSubWriter> writers)
			throws IOException {
		for (EasyJaSubWriter writer : writers) {
			writer.close();
		}
		writers.clear();
	}

	private String toHtml(SubtitleLine s, String cssFileRef,
			EasyJaSubInput command) throws IOException {
		HtmlCanvas html = new HtmlCanvas();
		html.write("<!DOCTYPE html>", false)
				.html(lang("ja"))
				.head()
				.meta(charset(EasyJaSubCharset.CHARSETSTR))
				.write("<link href=\"" + cssFileRef
						+ "\" rel=\"stylesheet\" type=\"text/css\" />", false)
				._head().body();
		renderOn(html, s, command);
		html._body()._html();

		return html.toHtml();
	}

	private static EasyJaSubWriter toFile(String text, File file)
			throws IOException {
		EasyJaSubWriter fw = new EasyJaSubWriter(file);
		fw.println(text);
		return fw;
	}


	private void renderOn(HtmlCanvas html, SubtitleLine line,
			EasyJaSubInput command) throws IOException {
		List<SubtitleItem> items = line.getItems();
		if (items != null) {
			appendItems(html, command.showFurigana(), command.showDictionary(),
					command.showRomaji(), command.showKanji(), items);
		} else {
			if (line.getSubText() != null) {
				appendSubText(html, line);
			}
		}
		if (command.showTranslation()) {
			appendTranslation(html, line);
		}
		if (items != null) {
			appendComment(html, items);
		}
	}

	private void appendComment(HtmlCanvas html, List<SubtitleItem> items)
			throws IOException {
		html.write("<!--", false);
		appendln(html);
		for (SubtitleItem item : items) {
			String comment = item.getComment();
			if (comment != null) {
				html.write(comment, false);
				appendln(html);
			}
		}
		html.write("-->", false);
		appendln(html);
	}

	private void appendTranslation(HtmlCanvas html, SubtitleLine line)
			throws IOException {
		String translation = line.getTranslation();
		if (translation != null) {
			appendln(html);
			html.p().write(translation, false)._p();
		}
	}

	private void appendSubText(HtmlCanvas html, SubtitleLine line)
			throws IOException {
		html.table().tr(class_("center")).td()
				.content(line.getSubText(), false)._tr()
				._table();
		appendln(html);
	}

	private static final String Newline = EasyJaSubWriter.Newline;

	private void appendItems(HtmlCanvas html, boolean showFurigana,
			boolean showDictionary, boolean showRomaji, boolean showKanji,
			List<SubtitleItem> items) throws IOException {
		appendln(html);
		html.table();
		appendln(html);
		if (showFurigana && showKanji) {
			html.tr(class_("top")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderFurigana(html, item);
				appendln(html);
			}
			html._tr();
			appendln(html);
		}
		html.tr(class_("center")).write(Newline, false);
		for (SubtitleItem item : items) {
			html.write("  ");
			renderOnCenter(html, item, showKanji);
			appendln(html);
		}
		html._tr();
		appendln(html);
		if (showRomaji) {
			html.tr(class_("bottom"));
			appendln(html);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderRomaji(html, item);
				appendln(html);
			}
			html._tr();
			appendln(html);
		}
		if (showDictionary) {
			html.tr(class_("translation")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderDictionary(html, item);
				appendln(html);
			}
			html._tr();
			appendln(html);
		}
		html._table();
	}

	private void appendln(HtmlCanvas html) throws IOException {
		html.write(Newline, false);
	}

	private void renderFurigana(HtmlCanvas html, SubtitleItem item)
			throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.td()._td();
		} else {
			html.td(class_(item.getGrammarElement())).content(furigana, false);
		}

	}

	private void renderOnCenter(HtmlCanvas html, SubtitleItem item,
			boolean showKanji) throws IOException {
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (text == null) {
			html.td()._td();
		} else if (elements == null) {
			String trimmedText = text.replace('　', ' ').trim();
			String styleClass = item.getGrammarElement();
			if (styleClass == null) {
				html.td();
			} else {
				html.td(class_(styleClass));
			}
			html.write(trimmedText, false);
			if (trimmedText.length() < text.length()) {
				html.write("&thinsp;", false);
			}
			html._td();
		} else if (showKanji) {
			html.td(class_(item.getGrammarElement()));
			for (SubtitleItem.Inner element : elements) {
				String kanji = element.getKanji();
				if (kanji != null) {
					html.span(class_("kk")).content(kanji, false);
				} else {
					html.write(element.getText());
				}
			}
			html._td();
		} else {
			html.td(class_(item.getGrammarElement())).content(
					item.getFurigana());
		}
	}

	private void renderRomaji(HtmlCanvas html, SubtitleItem item)
			throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.td()._td();
		} else {
			html.td(class_(item.getGrammarElement())).content(romaji, false);
		}

	}

	private void renderDictionary(HtmlCanvas html, SubtitleItem item)
			throws IOException {
		String dict = item.getTranslation();
		if (dict != null) {
			html.td().content(dict);
		} else {
			html.td()._td();
		}
	}
}