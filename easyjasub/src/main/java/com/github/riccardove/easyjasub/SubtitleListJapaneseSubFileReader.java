package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListJapaneseSubFileReader {

	public void readJapaneseSubtitles(SubtitleList s, File file, SubtitleFileType type) throws Exception {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(), stream);
		stream.close();
		s.setTitle(subs.getTitle());
		for (InputTextSubCaption c : subs.getCaptions()) {
			SubtitleLine line = s.add();
			line.setCaption(c);
		}
	}
}
