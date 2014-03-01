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
import java.util.List;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListJapaneseSubFileReader {

	public void readJapaneseSubtitles(SubtitleList s, File file, SubtitleFileType type,
			EasyJaSubObserver observer, EasyJaSubLinesSelection selection) 
			throws IOException, InputTextSubException {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(), stream);
		stream.close();
		s.setTitle(subs.getTitle());
		List<InputTextSubCaption> subsList = subs.getCaptions();
		if (selection == null) {
			for (InputTextSubCaption c : subsList) {
				addLine(s, c);
			}
		}
		else {
			int startIndex = selection.getStartLine();
			if (startIndex > 0) {
				startIndex--;
			}
			int endIndex = selection.getEndLine();
			int size = subsList.size();
			if (endIndex == 0 || endIndex >= size) {
				endIndex = size-1;
			}
			for (int i = startIndex; i<= endIndex; ++i) {
				addLine(s, subsList.get(i));
			}
			selection.setStartLine(s.first().getStartTime());
			selection.setEndTime(s.last().getEndTime());
		}
		s.setJapaneseSubWarnings(subs.getWarnings());
		s.setJapaneseSubDescription(subs.getDescription());
		s.setJapaneseSubLanguage(subs.getLanguage());
	}

	private void addLine(SubtitleList s, InputTextSubCaption caption) {
		SubtitleLine line = s.add();
		line.setStartTime(caption.getStart().getMSeconds());
		line.setEndTime(caption.getEnd().getMSeconds());
		String content = caption.getContent();
		String key = JapaneseChar.getJapaneseKey(content);
		if (key != null) {
			line.setJapaneseSubText(content);
			line.setJapaneseSubKey(key);
		}
		else {
			line.setSubText(content);
		}
	}
}
