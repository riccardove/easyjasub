package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListTranslatedSubFileReader {
	public void readTranslationSubtitles(SubtitleList s, File file, SubtitleFileType type,
			EasyJaSubObserver observer) throws IOException, InputTextSubException {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(), stream);
		stream.close();
		s.setTranslationTitle(subs.getTitle());
		s.setTranslatedSubDescription(subs.getDescription());
		s.setTranslatedSubLanguage(subs.getLanguage());
		s.setTranslatedSubWarnings(subs.getWarnings());
		for (InputTextSubCaption enCaption : subs.getCaptions()) {
			boolean added = false;
			for (SubtitleLine jaLine : s) {
				if (jaLine.isJa() &&
					compatibleWith(jaLine, enCaption)) {
					addTranslation(jaLine, enCaption);
					added = true;
				}
				else if (added) {
					break;
				}
			}
			if (!added) {
				for (SubtitleLine jaLine : s) {
					if (jaLine.isJa() &&
						approxCompatibleWith(jaLine, enCaption)) {
						addTranslation(jaLine, enCaption);
						if (!added) {
							added = true;
						}
						else {
							observer.onTranslatedSubDuplicated(enCaption.getContent(), enCaption.getStart().getMSeconds(), jaLine.getStartTime());
						}
					}
					else if (added) {
						break;
					}
				}
			}
			if (!added) {
				insertTranslation(s, enCaption);
			}
		}
		String lastTranslation = null;
		for (SubtitleLine jaLine : s) {
			if (jaLine.isJa())
			{
				if (!jaLine.isTranslation()) {
					if (lastTranslation != null) {
						addTranslation(jaLine, lastTranslation);
					}
				}
				else {
					lastTranslation = jaLine.getTranslation();
				}
			}
			else {
				lastTranslation = null;
			}
		}
	}

	private static void addTranslation(SubtitleLine jaLine, InputTextSubCaption enCaption) {
		addTranslation(jaLine, enCaption.getContent());
	}

	private static void addTranslation(SubtitleLine jaLine, String translatedLineText) {
		String translation = jaLine.getTranslation();
		String text = TranslationReplace.matcher(translatedLineText).replaceAll(" ");
		if (translation == null) {
			translation = text;
		}
		else {
			translation += BreakStr + text;
		}
		jaLine.setTranslatedText(translation);
	}
	

	private static final Pattern TranslationReplace = Pattern.compile("<br ?/>");
	private static final String BreakStr = "&nbsp;&nbsp;&nbsp;";
	
	public void addTranslation(String content) {
	}


	private static boolean startsAfter(SubtitleLine line, InputTextSubCaption enCaption) {
		return line.getStartTime() >= enCaption.getEnd().getMSeconds();
	}

	private static boolean endsBefore(SubtitleLine line, InputTextSubCaption enCaption) {
		return line.getEndTime() <= enCaption.getStart().getMSeconds();
	}

	private static void insertTranslation(SubtitleList lines, InputTextSubCaption enCaption) {
		for (int i = 0; i<lines.size(); ++i) {
			SubtitleLine line = lines.get(i);
			if (line.isJa() && startsAfter(line, enCaption)) {
				if (i>0 && !endsBefore(lines.get(i-1), enCaption)) {
					addTranslation(lines.get(i-1), enCaption);
				}
				else {
					SubtitleLine translationLine = lines.add(i);
					translationLine.setCaption(enCaption);
					translationLine.setStartTime(enCaption.getStart().getMSeconds());
					translationLine.setEndTime(enCaption.getEnd().getMSeconds());
					addTranslation(translationLine, enCaption);
				}
				break;
			}
		}
		
	}


	private static boolean compatibleWith(SubtitleLine line, InputTextSubCaption enCaption) {
		int startDiff = line.getStartTime() - enCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - enCaption.getEnd().getMSeconds();
		// both start and end of the line are nearby
		return 
				(Math.abs(startDiff) < 2000 || Math.abs(endDiff) < 2000);
	}

	private static  boolean approxCompatibleWith(SubtitleLine line, InputTextSubCaption enCaption) {
		int startDiff = line.getStartTime() - enCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - enCaption.getEnd().getMSeconds();
		// ja: ------------***************--------------
		// en: -------**************************--------
		if (startDiff > -500 && endDiff < 500) {
			// japanese line is a part of translation translation
			return true;
		}
		// ja: -------**************************--------
		// en: ------------***************--------------
		if (startDiff < 500 && endDiff > -500) {
			// translation line is a part of japanese translation
			return true;
		}
		
		int interStartDiff = enCaption.getEnd().getMSeconds() - line.getStartTime();
		int interEndDiff = line.getEndTime() - enCaption.getStart().getMSeconds();
		// ja: -------************----------------------
		// en: ------------***************--------------
		if (startDiff < 0 && endDiff < 0 && interEndDiff > 500) {
			// japanese line starts before translation line but they share 0.5 seconds
			return true;
		}
		// ja: ------------***************--------------
		// en: -------************----------------------
		if (startDiff > 0 && endDiff > 0 && interStartDiff > 500) {
			// japanese line starts after translation line but they share 0.5 seconds
			return true;
		}
		return false;
	}

}
