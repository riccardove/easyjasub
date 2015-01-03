package com.github.riccardove.easyjasub.rendersnake;

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

import static org.rendersnake.HtmlAttributesFactory.charset;
import static org.rendersnake.HtmlAttributesFactory.class_;
import static org.rendersnake.HtmlAttributesFactory.lang;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

public class RendersnakeHtmlCanvas {

	private final String newLineStr;

	public RendersnakeHtmlCanvas(String newLine) {
		newLineStr = newLine;
		html = new HtmlCanvas();
	}

	private final HtmlCanvas html;

	public void header(String cssFileRef, String charsetStr) throws IOException {
		html.write("<!DOCTYPE html>", false)
				.write(newLineStr, false)
				.html(lang("ja"))
				.write(newLineStr, false)
				.head()
				.write(newLineStr, false)
				.meta(charset(charsetStr))
				.write(newLineStr, false)
				.write("<link href=\"" + cssFileRef
						+ "\" rel=\"stylesheet\" type=\"text/css\" />", false)
				.write(newLineStr, false)._head().write(newLineStr, false)
				.body().write(newLineStr, false);
	}

	public void footer() throws IOException {
		html._body()._html();
	}

	@Override
	public String toString() {
		return html.toHtml();
	}

	public RendersnakeHtmlCanvas h1() throws IOException {
		html.h1();
		return this;
	}

	public RendersnakeHtmlCanvas _h1() throws IOException {
		html._h1();
		return this;
	}

	public RendersnakeHtmlCanvas h2() throws IOException {
		html.h2();
		return this;
	}

	public RendersnakeHtmlCanvas _h2() throws IOException {
		html._h2();
		return this;
	}

	public RendersnakeHtmlCanvas _ul() throws IOException {
		html._ul();
		return this;
	}

	public RendersnakeHtmlCanvas ul() throws IOException {
		html.ul();
		return this;
	}

	public RendersnakeHtmlCanvas li() throws IOException {
		html.li();
		return this;
	}

	public RendersnakeHtmlCanvas _li() throws IOException {
		html._li();
		return this;
	}

	public RendersnakeHtmlCanvas newline() throws IOException {
		html.write(newLineStr, false);
		return this;
	}

	public void table() throws IOException {
		html.table();
	}

	public void tr(String string) throws IOException {
		html.tr(class_(string)).write(newLineStr, false);
	}

	public void _tr() throws IOException {
		html._tr();
	}

	public void space() throws IOException {
		html.write("  ");
	}

	public void tdEmpty() throws IOException {
		html.td()._td();
	}

	public void td(String grammarElement, String furigana) throws IOException {
		html.td(class_(grammarElement)).content(furigana, false);
	}

	public void td(String dict) throws IOException {
		html.td().content(dict);
	}

	public RendersnakeHtmlCanvas write(String trimmedText) throws IOException {
		html.write(trimmedText, true);
		return this;
	}

	public void writeUnescaped(String trimmedText) throws IOException {
		html.write(trimmedText, false);
	}

	public void tdOpen(String styleClass) throws IOException {
		html.td(class_(styleClass));
	}

	public void td() throws IOException {
		html.td();
	}

	public void _td() throws IOException {
		html._td();
	}

	public void spanKanji(String kanji) throws IOException {
		html.span(class_("kk")).content(kanji, false);
	}

	public void span(String styleClass) throws IOException {
		html.span(class_(styleClass));
	}

	public void _span() throws IOException {
		html._span();
	}

	public void _table() throws IOException {
		html._table();
	}

	public void p(String translation) throws IOException {
		html.p(class_("translation")).write(translation, false)._p();
	}

	public void table(String string, String subText) throws IOException {
		html.table().tr(class_(string)).td().content(subText, false)._tr()
				._table();
	}

	public void comment() throws IOException {
		html.write("<!--", false);
		newline();
	}

	public void _comment() throws IOException {
		html.write("-->", false);
		newline();
	}

	public RendersnakeHtmlCanvas p() throws IOException {
		html.p(class_("ja"));
		return this;
	}

	public RendersnakeHtmlCanvas _p() throws IOException {
		html._p();
		return this;
	}

	public void ruby() throws IOException {
		html.ruby();
	}

	public void ruby(String grammarElement) throws IOException {
		html.ruby(class_(grammarElement));
	}

	public void rt(String furigana) throws IOException {
		html.rt().write(furigana, false)._rt();
	}

	public void _ruby() throws IOException {
		html._ruby();
	}

	public void div() throws IOException {
		html.div();
	}

	public void _div() throws IOException {
		html._div();
	}
}
