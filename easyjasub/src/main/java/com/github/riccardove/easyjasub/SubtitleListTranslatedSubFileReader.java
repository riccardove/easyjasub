package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListTranslatedSubFileReader {
	public void readEnglishSubtitles(SubtitleList s, String fileName, File file) throws Exception {
		InputTextSubFile subs = parseAssFile(fileName, file);
		s.setEnglishTitle(subs.title);
		for (InputTextSubCaption enCaption : subs.getCaptions()) {
			boolean added = false;
			for (SubtitleLine jaLine : s) {
				if (jaLine.isJa() &&
					jaLine.compatibleWith(enCaption)) {
					jaLine.addEnglish(enCaption.getContent());
					added = true;
				}
				else if (added) {
					break;
				}
			}
			if (!added) {
				for (SubtitleLine jaLine : s) {
					if (jaLine.isJa() &&
						jaLine.approxCompatibleWith(enCaption)) {
						jaLine.addEnglish(enCaption.getContent());
						if (!added) {
							added = true;
						}
						else {
							System.out.println("Duplicated english caption " + enCaption.getContent() + " starting at " + enCaption.getStart() + " at " + jaLine.getIdxStartTime());
						}
					}
					else if (added) {
						break;
					}
				}
			}
			if (!added) {
				s.insertEnglish(enCaption);
			}
		}
		String lastEnglish = null;
		for (SubtitleLine jaLine : s) {
			if (jaLine.isJa())
			{
				if (!jaLine.isEnglish()) {
					if (lastEnglish != null) {
						jaLine.addEnglish(lastEnglish);
					}
				}
				else {
					lastEnglish = jaLine.getEnglish();
				}
			}
			else {
				lastEnglish = null;
			}
		}
	}

	private static InputTextSubFile parseAssFile(String fileName, File file) throws IOException,
			Exception {
		return new InputTextSubFile("ASS", fileName, new FileInputStream(file));
	}

}
