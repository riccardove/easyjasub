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

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleListToHtmlFileWriter {

	public void writeFile(EasyJaSubInput command, SubtitleList list,
			EasyJaSubCssFile cssFile, File file, File htmlDirectory)
			throws IOException {

		String cssFileUrl = cssFile != null ? cssFile
				.toRelativeURIStr(htmlDirectory) : "default.css";

		SubtitleLineToHtml converter = new SubtitleLineToHtml(
				command.isSingleLine(),
				command.getWkHtmlToImageCommand() != null,
				command.showFurigana(), command.showRomaji(),
				command.showDictionary(), command.showKanji(),
				command.showTranslation());

		String htmlStr = toHtml(converter, list, cssFileUrl);

		// TODO avoid storing the full html in memory
		EasyJaSubWriter writer = new EasyJaSubWriter(file);
		writer.print(htmlStr);
		writer.close();
	}

	private String toHtml(SubtitleLineToHtml converter, SubtitleList list,
			String cssFileRef) throws IOException {
		RendersnakeHtmlCanvas html = converter.createHtmlCanvas(cssFileRef);
		html.h1().write("Text")._h1();
		for (SubtitleLine line : list) {
			html.div();
			int s = line.getStartTime() / 1000;
			String time = s / 60 + ":" + s % 60;
			html.h2().write(time)._h2();
			converter.appendHtmlBodyContent(line, html);
			html._div();
		}
		html.h1().write("Vocabulary")._h1();
		writeVocabolary(html, list);
		html.footer();
		return html.toString();
	}

	private void writeVocabolary(RendersnakeHtmlCanvas html, SubtitleList list)
			throws IOException {
		TreeMap<String, HashSet<String>> partOfSpeechToItem = new TreeMap<String, HashSet<String>>();
		for (SubtitleLine line : list) {
			if (line.getItems() != null) {
				for (SubtitleItem item : line.getItems()) {
					String vocabKey = item.getBaseForm();
					if (vocabKey == null) {
						vocabKey = item.getText();
					}
					if (vocabKey != null) {
						String pos = item.getPartOfSpeech();
						if (pos != null && !isPartOfSpeechEasy(pos)) {
							HashSet<String> items = partOfSpeechToItem.get(pos);
							if (items == null) {
								items = new HashSet<String>();
								partOfSpeechToItem.put(pos, items);
							}
							items.add(vocabKey);
						}
					}
				}
			}
		}
		for (Entry<String, HashSet<String>> a : partOfSpeechToItem.entrySet()) {
			String pos = a.getKey();
			html.newline().h2().write(pos)._h2().newline().ul().newline();
			TreeSet<String> itemsSorted = new TreeSet<String>(a.getValue());
			for (String vocabKey : itemsSorted) {
				html.li().write(vocabKey)._li().newline();
			}
			html._ul().newline();
		}
	}

	private static boolean isPartOfSpeechEasy(String pos) {
		if (pos.startsWith("symbol") || pos.startsWith("particle")) {
			return true;
		}
		switch (pos) {
		case "noun-numeric":
		case "filler":
			return true;
		}
		return false;
	}
}
