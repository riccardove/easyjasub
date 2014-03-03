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

class SubtitleListCssFileWriter {
	
	public SubtitleListCssFileWriter(File file,
			EasyJaSubInput command) throws IOException {
		writer = new EasyJaSubWriter(file);
		hiraganaFont = command.getCssHiraganaFont();
		kanjiFont = command.getCssKanjiFont();
		translationFont = command.getCssTranslationFont();
	}
	
	private final EasyJaSubWriter writer;
	private final String hiraganaFont;
	private final String kanjiFont;
	private final String translationFont;
	
	private void w(String line) throws IOException {
		writer.println(line);
	}
	
	private void w() throws IOException {
		writer.println();
	}
	
	public void write() throws IOException {
		// TODO: allow specifying sizes
		w("body {");
		w("	text-shadow: -2px 0 black, 0 2px black, 2px 0 black, 0 -2px black;");
		w("	color: white;");
		w("	text-align: center;");
		w("}");
		w();
		w("p {");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		if (translationFont != null) {
			w("	font-family: " + translationFont + ";");
		}
		w("	font-size: 14pt;");
		w("}");
		w();
		w("table {");
		w("	letter-spacing: -4px;");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		w("}");
		w();
		w("tr.top {");
		w("	letter-spacing: -2px;");
		if (hiraganaFont != null) {
			w("	font-family: " + hiraganaFont + ";");
		}
		w("	font-size: 18pt;");
		w("}");
		w();
		w("tr.bottom {");
		if (translationFont != null) {
			w("	font-family: " + translationFont + ";");
		}
		w("	font-size: 12pt;");
		w("	letter-spacing: 3px;");
		w("}");
		w();
		w("tr.center {");
		w("	padding-left: 2px;");
		w("	padding-right: 2px;");
		w("}");
		w();
		w("tr.translation {");
		if (translationFont != null) {
			w("	font-family: " + translationFont + ";");
		}
		w("	font-size: 12pt;");
		w("	text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;");
		w("	letter-spacing: 1px;");
		w("}");
		w();
		w("td {");
		w("	text-align: center;");
		w("}");
		w();
		w("tr {");
		if (hiraganaFont != null) {
			w("	font-family: " + hiraganaFont + ";");
		}
		w("	font-size: 25pt;");
		w("}");
		w();
		w("span.kk {");
		w("	letter-spacing: -6px;");
		if (kanjiFont != null) {
			w("	font-family: " + kanjiFont + ";");
		}
		w("	font-size: 40pt;");
		w("	margin-left: 0;");
		w("	margin-right: 0;");
		w("	padding: 0;");
		w("}");
		w();
		writer.close();
	}
}
