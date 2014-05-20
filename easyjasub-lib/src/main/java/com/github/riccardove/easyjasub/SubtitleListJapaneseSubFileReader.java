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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListJapaneseSubFileReader {

	public void readJapaneseSubtitles(SubtitleList s, File file,
			SubtitleFileType type, EasyJaSubObserver observer,
			EasyJaSubLinesSelection selection) throws IOException,
			InputTextSubException {
		FileInputStream stream = new FileInputStream(file);
		List<InputSubtitleLine> subsList = null;
		if (type == SubtitleFileType.TXT) {
			subsList = readText(stream);
			s.setTitle("Text file " + file.getName());
			s.setJapaneseSubDescription("Read from text file "
					+ file.getAbsolutePath());
		} else {
			InputTextSubFile subs = new InputTextSubFile(type, file.getName(),
					stream);
			s.setTitle(subs.getTitle());
			s.setJapaneseSubWarnings(subs.getWarnings());
			s.setJapaneseSubDescription(subs.getDescription());
			s.setJapaneseSubLanguage(subs.getLanguage());
			subsList = subs.getCaptions();
		}
		stream.close();
		addLines(s, selection, subsList);
	}

	private List<InputSubtitleLine> readText(FileInputStream stream)
			throws IOException {
		EasyJaSubReader reader = new EasyJaSubReader(stream);
		ArrayList<InputSubtitleLine> list = new ArrayList<InputSubtitleLine>();
		int time = 0;
		final int increment = 3000;
		for (String text = reader.readLine(); text != null; text = reader
				.readLine()) {
			InputSubtitleLine line = new InputSubtitleLine();
			line.setStartTime(time);
			line.setEndTime(time += increment);
			line.setContent(text);
			list.add(line);
		}
		return list;
	}

	private void addLines(SubtitleList s, EasyJaSubLinesSelection selection,
			List<InputSubtitleLine> subsList) {
		if (selection == null) {
			for (InputSubtitleLine c : subsList) {
				addLine(s, c);
			}
		} else {
			int startIndex = selection.getStartLine();
			if (startIndex > 0) {
				startIndex--;
			}
			int endIndex = selection.getEndLine();
			int size = subsList.size();
			if (endIndex == 0 || endIndex > size) {
				endIndex = size;
			}
			for (int i = startIndex; i < endIndex; ++i) {
				addLine(s, subsList.get(i));
			}
			selection.setStartLine(s.first().getStartTime());
			selection.setEndTime(s.last().getEndTime());
		}
	}

	private void addLine(SubtitleList s, InputSubtitleLine caption) {
		String content = getContent(caption);
		if (content == null) {
			return;
		}
		SubtitleLine line = s.add();
		line.setStartTime(caption.getStartTime());
		line.setEndTime(caption.getEndTime());
		line.setSubText(content);
	}

	public static String getContent(InputSubtitleLine caption) {
		String content = caption.getContent();
		if (content == null) {
			return null;
		}
		content = content.trim();
		if (content.length() == 0) {
			return null;
		}
		return replaceNewlines(content);
	}

	private static String replaceNewlines(String content) {
		return TranslationReplace.matcher(content).replaceAll(" ");
	}

	private static final Pattern TranslationReplace = Pattern
			.compile(" *<br ?/> *");
}
