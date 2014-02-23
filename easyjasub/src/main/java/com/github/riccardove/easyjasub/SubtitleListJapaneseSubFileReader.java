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

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListJapaneseSubFileReader {

	public void readJapaneseSubtitles(SubtitleList s, File file, SubtitleFileType type,
			EasyJaSubObserver observer) 
			throws IOException, InputTextSubException {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(), stream);
		stream.close();
		s.setTitle(subs.getTitle());
		for (InputTextSubCaption c : subs.getCaptions()) {
			SubtitleLine line = s.add();
			line.setCaption(c); // TODO remove
			line.setStartTime(c.getStart().getMSeconds());
			line.setEndTime(c.getEnd().getMSeconds());
			line.setJapaneseSubText(c.getContent());
		}
		s.setJapaneseSubWarnings(subs.getWarnings());
		s.setJapaneseSubDescription(subs.getDescription());
		s.setJapaneseSubLanguage(subs.getLanguage());
	}
}
