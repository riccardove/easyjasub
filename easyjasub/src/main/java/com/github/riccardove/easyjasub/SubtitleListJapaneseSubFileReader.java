package com.github.riccardove.easyjasub;

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
