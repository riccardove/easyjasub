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
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListTranslatedSubFileReader {
	
	private int msecondsMatch;
	private int msecondsApproxMatch;

	public SubtitleListTranslatedSubFileReader(int msecondsMatch, int msecondsApproxMatch) {
		this.msecondsMatch = msecondsMatch;
		this.msecondsApproxMatch = msecondsApproxMatch;
	}
	
	public void readTranslationSubtitles(SubtitleList s, File file, SubtitleFileType type,
			EasyJaSubObserver observer, EasyJaSubLinesSelection selection, boolean showTranslation) throws IOException, InputTextSubException {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(), stream);
		stream.close();
		s.setTranslationTitle(subs.getTitle());
		s.setTranslatedSubDescription(subs.getDescription());
		s.setTranslatedSubLanguage(subs.getLanguage());
		s.setTranslatedSubWarnings(subs.getWarnings());
		for (InputTextSubCaption translatedCaption : subs.getCaptions()) {
			boolean added = false;
			for (SubtitleLine jaLine : s) {
				if (isJa(jaLine) &&
					compatibleWith(jaLine, translatedCaption)) {
					addTranslation(jaLine, translatedCaption);
					added = true;
				}
				else if (added) {
					break;
				}
			}
			if (!added) {
				for (SubtitleLine jaLine : s) {
					if (isJa(jaLine) &&
						approxCompatibleWith(jaLine, translatedCaption)) {
						addTranslation(jaLine, translatedCaption);
						if (!added) {
							added = true;
						}
						else {
							observer.onTranslatedSubDuplicated(translatedCaption.getContent(), translatedCaption.getStart().getMSeconds(), jaLine.getStartTime());
						}
					}
					else if (added) {
						break;
					}
				}
			}
			if (!added && showTranslation) {
				insertTranslation(s, translatedCaption, selection);
			}
		}
		String lastTranslation = null;
		for (SubtitleLine jaLine : s) {
			if (isJa(jaLine))
			{
				if (!isTranslation(jaLine)) {
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

	private void addTranslation(SubtitleLine jaLine, InputTextSubCaption enCaption) {
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

	private boolean startsAfter(SubtitleLine line, InputTextSubCaption translatedCaption) {
		return line.getStartTime() >= translatedCaption.getEnd().getMSeconds();
	}

	private boolean endsBefore(SubtitleLine line, InputTextSubCaption translatedCaption) {
		return line.getEndTime() <= translatedCaption.getStart().getMSeconds();
	}

	private void insertTranslation(SubtitleList lines, InputTextSubCaption translatedCaption,
			EasyJaSubLinesSelection selection) {
		// TODO: avoid inserting lines that match a non-japanese line with just getSubText()
		for (int i = 0; i<lines.size(); ++i) {
			SubtitleLine line = lines.get(i);
			if (isJa(line) && startsAfter(line, translatedCaption)) {
				if (i>0 && !endsBefore(lines.get(i-1), translatedCaption)) {
					addTranslation(lines.get(i-1), translatedCaption);
				}
				else {
					if (selection == null || isTimeCompatibleWithSelection(translatedCaption, selection)) {
						SubtitleLine translationLine = lines.add(i);
						translationLine.setStartTime(translatedCaption.getStart().getMSeconds());
						translationLine.setEndTime(translatedCaption.getEnd().getMSeconds());
						addTranslation(translationLine, translatedCaption);
					}
				}
				break;
			}
		}
		
	}

	private boolean isTimeCompatibleWithSelection(
			InputTextSubCaption translatedCaption, EasyJaSubLinesSelection selection) {
		if (selection.getEndTime() > 0 && translatedCaption.getStart().getMSeconds() > selection.getEndTime()) {
			return false;
		}
		if (selection.getStartTime() > 0 && translatedCaption.getEnd().getMSeconds() < selection.getStartTime()) {
			return false;
		}
		return true;
	}

	private static boolean isJa(SubtitleLine line) {
		return line.getJapanese() != null;
	}

	private static boolean isTranslation(SubtitleLine line) {
		return line.getTranslation() != null;
	}

	private boolean compatibleWith(SubtitleLine line, InputTextSubCaption translatedCaption) {
		int startDiff = line.getStartTime() - translatedCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - translatedCaption.getEnd().getMSeconds();
		// both start and end of the line are nearby
		return 
				(Math.abs(startDiff) < msecondsMatch || Math.abs(endDiff) < msecondsMatch);
	}

	private boolean approxCompatibleWith(SubtitleLine line, InputTextSubCaption translatedCaption) {
		// TODO: avoid that captions that have end time near the start of the subsequent caption are evaluated for match at the end
		int startDiff = line.getStartTime() - translatedCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - translatedCaption.getEnd().getMSeconds();
		// ja: ------------***************--------------
		// en: -------**************************--------
		if (startDiff > -msecondsApproxMatch && endDiff < msecondsApproxMatch) {
			// japanese line is a part of translation translation
			return true;
		}
		// ja: -------**************************--------
		// en: ------------***************--------------
		if (startDiff < msecondsApproxMatch && endDiff > -msecondsApproxMatch) {
			// translation line is a part of japanese translation
			return true;
		}
		
		int interStartDiff = translatedCaption.getEnd().getMSeconds() - line.getStartTime();
		int interEndDiff = line.getEndTime() - translatedCaption.getStart().getMSeconds();
		// ja: -------************----------------------
		// en: ------------***************--------------
		if (startDiff < 0 && endDiff < 0 && interEndDiff > msecondsApproxMatch) {
			// japanese line starts before translation line but they share 0.5 seconds
			return true;
		}
		// ja: ------------***************--------------
		// en: -------************----------------------
		if (startDiff > 0 && endDiff > 0 && interStartDiff > msecondsApproxMatch) {
			// japanese line starts after translation line but they share 0.5 seconds
			return true;
		}
		return false;
	}

}
