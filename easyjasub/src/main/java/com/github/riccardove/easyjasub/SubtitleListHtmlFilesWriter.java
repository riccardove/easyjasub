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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.rendersnake.HtmlCanvas;

class SubtitleListHtmlFilesWriter {
	private final String cssFile;
	private final EasyJaSubObserver observer;

	public SubtitleListHtmlFilesWriter(String cssFileUrl, EasyJaSubObserver observer){
		this.cssFile = cssFileUrl;
		this.observer = observer;
	}

	public void writeHtmls(SubtitleList s, EasyJaSubInput command) throws IOException{
		ArrayList<OutputStreamWriter> writers = new ArrayList<OutputStreamWriter>(s.size());
		for (SubtitleLine l : s) {
			File file = l.getHtmlFile();
			if (!file.exists()) {
				observer.onWriteHtmlFile(file);
				String htmlStr = toHtml(l, cssFile, command);
				
				writers.add(toFile(htmlStr, file));
			}
			else {
				observer.onWriteHtmlFileSkipped(file);
			}
		}
		for (OutputStreamWriter writer : writers) {
			writer.close();
		}
	}
	
	private String toHtml(SubtitleLine s, String cssFileRef, EasyJaSubInput command) throws IOException 
	{
		HtmlCanvas html = new HtmlCanvas();
		html.write("<!DOCTYPE html>", false)
		.html(lang("ja"))
		.head()
		.meta(charset(EasyJaSubCharset.CHARSETSTR))
		.write("<link href=\"" + cssFileRef + "\" rel=\"stylesheet\" type=\"text/css\" />", false)
		._head().body();
		renderOn(html, s, command);
		html._body()._html();

		return html.toHtml();
	}
	
	private static OutputStreamWriter toFile(String text, File fileName) throws IOException
	{
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fileName), EasyJaSubCharset.CHARSET);
		fw.write(text);
		return fw;
	}


	public static final String Newline = "\r\n";
	
	private void renderOn(HtmlCanvas html, SubtitleLine line, EasyJaSubInput command) throws IOException {
		boolean showTranslation = command.showTranslation();
		boolean showFurigana = command.showFurigana();
		boolean showDictionary = command.showDictionary();
		boolean showRomaji = command.showRomaji();
		boolean showKanji = command.showKanji();
		
		List<SubtitleItem> items = line.getItems();
		if (items != null) {
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
		if (showTranslation) {
			String translation = line.getTranslation();
			if (translation != null) {
				appendln(html);
				html.p().write(translation, false)._p();
			}
		}
		if (items != null) {
			html.write("<!--", false);
			appendln(html);
			for (SubtitleItem item : items) {
				String comment = item.getComment();
				if (comment!=null) {
					html.write(comment, false);
					appendln(html);
				}
			}
			html.write("-->", false);
			appendln(html);
		}
	}

	private void appendln(HtmlCanvas html) throws IOException {
		html.write(Newline, false);
	}
	
	private void renderFurigana(HtmlCanvas html, SubtitleItem item) throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.td()._td();
		}
		else {
			html.td(class_(item.getGrammarElement())).content(furigana, false);
		}

	}
	private void renderOnCenter(HtmlCanvas html, SubtitleItem item, boolean showKanji) throws IOException {
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (text == null) {
			html.td()._td();
		}
		else if (elements == null){
			String trimmedText = text.replace('　', ' ').trim();
			String styleClass = item.getGrammarElement();
			if (styleClass == null) {
				html.td();
			}
			else {
				html.td(class_(styleClass));
			}
			html.write(trimmedText, false);
			if (trimmedText.length() < text.length()) {
				html.write("&thinsp;", false);
			}
			html._td();
		}
		else if (showKanji) {
			html.td(class_(item.getGrammarElement()));
			for (SubtitleItem.Inner element : elements) {
				String kanji = element.getKanji();
				if (kanji != null) {
		    		html.span(class_("kk")).content(kanji, false);
				}
				else {
					html.write(element.getText());
				}
			}
			html._td();
		}
		else {
			html.td(class_(item.getGrammarElement())).content(item.getFurigana());
		}
	}
	
	private void renderRomaji(HtmlCanvas html, SubtitleItem item) throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.td()._td();
		}
		else {
			html.td(class_(item.getGrammarElement())).content(romaji, false);
		}

	}

	private void renderDictionary(HtmlCanvas html, SubtitleItem item) throws IOException {
		String dict = item.getTranslation();
		if (dict != null) {
			html.td().content(dict);
		}
		else {
			html.td()._td();
		}
	}
}
