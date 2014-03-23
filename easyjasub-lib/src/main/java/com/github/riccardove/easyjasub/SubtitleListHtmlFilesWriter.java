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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

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

	private static String toRelativeURIStr(File file, File directory) {
		return directory.toURI().relativize(file.toURI()).toString();
	}

	public void writeHtmls(SubtitleList s, EasyJaSubInput command)
			throws IOException {
		// TODO construct relative url
		String cssFileUrl = cssFile != null ? toRelativeURIStr(cssFile,
				htmlDirectory) : "default.css";

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
		RendersnakeHtmlCanvas html = new RendersnakeHtmlCanvas(
				EasyJaSubWriter.Newline);
		html.header(cssFileRef, EasyJaSubCharset.CHARSETSTR);

		List<SubtitleItem> items = s.getItems();
		if (items != null) {
			html.newline();
			if (command.getWkHtmlToImageCommand() != null
					&& !command.showRomaji() && !command.showDictionary()
					&& command.showFurigana() && command.showKanji()) {
				appendRubyItems(html, items);
			} else {
				appendItems(html, command.showFurigana(),
						command.showDictionary(), command.showRomaji(),
						command.showKanji(), items);
			}
		} else {
			if (s.getSubText() != null) {
				appendSubText(html, s);
			}
		}
		if (command.showTranslation()) {
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
			if (item.getText() != null) {
				String furigana = item.getFurigana();
				if (furigana == null) {
					appendText(html, item.getText());
				} else {
					appendRuby(html, item);
				}
			}
		}

		html._p();
	}

	private void appendRuby(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (elements == null) {
			appendText(html, text);
		} else {
			html.ruby(item.getGrammarElement());
			appendElements(html, elements);
			html.rt(item.getFurigana());
			html._ruby();
		}
	}

	private static EasyJaSubWriter toFile(String text, File file)
			throws IOException {
		EasyJaSubWriter fw = new EasyJaSubWriter(file);
		fw.println(text);
		return fw;
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

	private void appendItems(RendersnakeHtmlCanvas html, boolean showFurigana,
			boolean showDictionary, boolean showRomaji, boolean showKanji,
			List<SubtitleItem> items) throws IOException {
		html.table();
		html.newline();
		if (showFurigana && showKanji) {
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
			renderOnCenter(html, item, showKanji);
			html.newline();
		}
		html._tr();
		html.newline();
		if (showRomaji) {
			html.tr("bottom");
			for (SubtitleItem item : items) {
				html.write("  ");
				renderRomaji(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		if (showDictionary) {
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
		String dict = item.getTranslation();
		if (dict != null) {
			html.td(dict);
		} else {
			html.tdEmpty();
		}
	}
}
