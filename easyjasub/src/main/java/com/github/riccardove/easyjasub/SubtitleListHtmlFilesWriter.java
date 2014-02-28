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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rendersnake.HtmlCanvas;

class SubtitleListHtmlFilesWriter {
	private final File htmlFolder;
	private final String cssFile;
	private EasyJaSubObserver observer;

	public SubtitleListHtmlFilesWriter(File htmlFolder, String cssFileUrl, EasyJaSubObserver observer){
		this.htmlFolder = htmlFolder;
		this.cssFile = cssFileUrl;
		this.observer = observer;
	}

	public void writeHtmls(SubtitleList s) throws IOException{
		ArrayList<FileWriter> writers = new ArrayList<FileWriter>(s.size());
		for (SubtitleLine l : s) {
			File file = l.getHtmlFile();
			if (!file.exists()) {
				observer.onWriteHtmlFile(file);
				String htmlStr = toHtml(l, cssFile);
				
				writers.add(toFile(htmlStr, file));
			}
			else {
				observer.onWriteHtmlFileSkipped(file);
			}
		}
		for (FileWriter writer : writers) {
			writer.close();
		}
	}
	
	private String toHtml(SubtitleLine s, String cssFileRef) throws IOException 
	{
		HtmlCanvas html = new HtmlCanvas();
		html.write("<!DOCTYPE html>", false)
		.html(lang("ja"))
		.head()
		.meta(charset("utf-8"))
		.write("<link href=\"" + cssFileRef + "\" rel=\"stylesheet\" type=\"text/css\" />", false)
		._head().body();
		renderOn(html, s);
		html._body()._html();

		return html.toHtml();
	}
	
	private static FileWriter toFile(String text, File fileName) throws IOException
	{
		FileWriter fw = new FileWriter(fileName);
		fw.write(text);
		return fw;
	}


	public static final String Newline = "\r\n";
	
	private void renderOn(HtmlCanvas html, SubtitleLine line) throws IOException {
		List<SubtitleItem> items = line.getItems();
		if (items != null) {
			html.write(Newline, false).table().write(Newline, false).tr(class_("top")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderOnTop(html, item);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("center")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderOnCenter(html, item);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("bottom")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderOnBottom(html, item);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("translation")).write(Newline, false);
			for (SubtitleItem item : items) {
				html.write("  ");
				renderOnLast(html, item);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false)._table();
		}
		String translation = line.getTranslation();
		if (translation != null) {
			html.write(Newline, false)
			.p().write(translation, false)._p();
		}
		if (items != null) {
			html.write("<!--" + Newline, false);
			for (SubtitleItem item : items) {
				String comment = item.getComment();
				if (comment!=null) {
					html.write(comment + Newline, false);
				}
			}
			html.write("-->" + Newline, false);
		}
	}

	
	private void renderOnTop(HtmlCanvas html, SubtitleItem item) throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.td()._td();
		}
		else {
			html.td(class_(item.getGrammarElement())).content(furigana);
		}

	}
	private void renderOnCenter(HtmlCanvas html, SubtitleItem item) throws IOException {
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
		else {
			html.td(class_(item.getGrammarElement()));
			for (SubtitleItem.Inner element : elements) {
				String kanji = element.getKanji();
				if (kanji != null) {
		    		html.span(class_("kk")).content(kanji);
				}
				else {
					html.write(element.getText());
				}
			}
			html._td();
		}
	}
	private void renderOnBottom(HtmlCanvas html, SubtitleItem item) throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.td()._td();
		}
		else {
			html.td(class_(item.getGrammarElement())).content(romaji);
		}

	}

	
// RED	public void renderOn(HtmlCanvas html) throws IOException {
//		html.span(class_(posc)).write(text)._span();
//	}

	private void renderOnLast(HtmlCanvas html, SubtitleItem item) throws IOException {
		String dict = item.getTranslation();
		if (dict != null) {
			html.td().content(dict);
		}
		else {
			html.td()._td();
		}
	}
}
