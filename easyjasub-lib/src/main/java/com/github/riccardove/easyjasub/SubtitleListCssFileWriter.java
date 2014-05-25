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
		final int shadow = 2;
		final int kanjiSize = 40;
		final int hiraganaSize = kanjiSize / 8 * 5;
		final int inlineTranslationSize = hiraganaSize / 2;
		final int furiganaSize = hiraganaSize * 4 / 5;
		final int translationSize = hiraganaSize * 2 / 3;
		final int lineHeight = (kanjiSize + furiganaSize) + 15;
		final int kanjiSpacing = -(kanjiSize / 12);
		final int hiraganaSpacing = 0; // -(hiraganaSize / 10);
		w("body {");
		w("	text-shadow: -" + shadow + "px 0 black, 0 " + shadow + "px black, "
				+ shadow + "px 0 black, 0 -" + shadow + "px black;");
		w("	color: white;");
		w("	text-align: center;");
		w("}");
		w();
		w("p {");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		w("	margin-bottom: 3pt;");
		w("	margin-top: 3pt;");
		w("}");
		w();
		w("p.translation {");
		if (translationFont != null) {
			w("	font-family: " + translationFont + ";");
		}
		w("	font-size: " + translationSize + "pt;");
		w("}");
		w();
		w("table {");
		w("	letter-spacing: " + hiraganaSpacing + "px;");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		w("}");
		w();
		w("p.ja {");
		w("	letter-spacing: " + hiraganaSpacing + "px;");
		if (hiraganaFont != null) {
			w("	font-family: " + hiraganaFont + ";");
		}
		w("	font-size: " + hiraganaSize + "pt;");
		w("	line-height: " + lineHeight + "pt;");
		w("}");
		w();
		w("tr.top {");
		w("	letter-spacing: " + hiraganaSpacing + "px;");
		if (hiraganaFont != null) {
			w("	font-family: " + hiraganaFont + ";");
		}
		w("	font-size: " + furiganaSize + "pt;");
		w("}");
		w();
		w("rt {");
		w("	letter-spacing: " + hiraganaSpacing + "px;");
		if (hiraganaFont != null) {
			w("	font-family: " + hiraganaFont + ";");
		}
		w("	font-size: " + furiganaSize + "pt;");
		w("}");
		w();
		w("tr.bottom {");
		if (translationFont != null) {
			w("	font-family: " + translationFont + ";");
		}
		w("	font-size: " + translationSize + "pt;");
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
		w("	font-size: " + inlineTranslationSize + "pt;");
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
		w("	font-size: " + hiraganaSize + "pt;");
		w("}");
		w();
		w("span.kk {");
		w("	letter-spacing: " + kanjiSpacing + "px;");
		if (kanjiFont != null) {
			w("	font-family: " + kanjiFont + ";");
		}
		w("	font-size: " + kanjiSize + "pt;");
		w("	margin-left: 0;");
		w("	margin-right: 0;");
		w("	padding: 0;");
		w("}");
		w();
		writer.close();
	}
}
