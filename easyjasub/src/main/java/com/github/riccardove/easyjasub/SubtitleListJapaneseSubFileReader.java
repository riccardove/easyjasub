package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListJapaneseSubFileReader {

	public void readJapaneseSubtitles(SubtitleList s, String fileName, File file) throws Exception {
		InputTextSubFile subs = parseAssFile(fileName, file);
		s.setTitle(subs.title);
		Iterator<InputTextSubCaption> ci = subs.getCaptions().iterator(); 
		Iterator<SubtitleLine> si = s.iterator();
		while (ci.hasNext() && si.hasNext()) {
			si.next().setCaption(ci.next());
		}
	}


	private static InputTextSubFile parseAssFile(String fileName, File file) throws IOException,
			Exception {
		return new InputTextSubFile("ASS", fileName, new FileInputStream(file));
	}

}
