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

import com.github.riccardove.easyjasub.inputnihongojtalk.NihongoJTalkSubtitleLine;
import com.github.riccardove.easyjasub.inputnihongojtalk.NihongoJTalkSubtitleList;
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
	
	public void readTranslationSubtitles(NihongoJTalkSubtitleList s, File file, SubtitleFileType type,
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
			for (NihongoJTalkSubtitleLine jaLine : s) {
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
				for (NihongoJTalkSubtitleLine jaLine : s) {
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
		for (NihongoJTalkSubtitleLine jaLine : s) {
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

	private void addTranslation(NihongoJTalkSubtitleLine jaLine, InputTextSubCaption enCaption) {
		addTranslation(jaLine, enCaption.getContent());
	}

	private static void addTranslation(NihongoJTalkSubtitleLine jaLine, String translatedLineText) {
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

	private boolean startsAfter(NihongoJTalkSubtitleLine line, InputTextSubCaption enCaption) {
		return line.getStartTime() >= enCaption.getEnd().getMSeconds();
	}

	private boolean endsBefore(NihongoJTalkSubtitleLine line, InputTextSubCaption enCaption) {
		return line.getEndTime() <= enCaption.getStart().getMSeconds();
	}

	private void insertTranslation(NihongoJTalkSubtitleList lines, InputTextSubCaption enCaption) {
		for (int i = 0; i<lines.size(); ++i) {
			NihongoJTalkSubtitleLine line = lines.get(i);
			if (line.isJa() && startsAfter(line, enCaption)) {
				if (i>0 && !endsBefore(lines.get(i-1), enCaption)) {
					addTranslation(lines.get(i-1), enCaption);
				}
				else {
					NihongoJTalkSubtitleLine translationLine = lines.add(i);
					translationLine.setStartTime(enCaption.getStart().getMSeconds());
					translationLine.setEndTime(enCaption.getEnd().getMSeconds());
					addTranslation(translationLine, enCaption);
				}
				break;
			}
		}
		
	}


	private boolean compatibleWith(NihongoJTalkSubtitleLine line, InputTextSubCaption enCaption) {
		int startDiff = line.getStartTime() - enCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - enCaption.getEnd().getMSeconds();
		// both start and end of the line are nearby
		return 
				(Math.abs(startDiff) < msecondsMatch || Math.abs(endDiff) < msecondsMatch);
	}

	private boolean approxCompatibleWith(NihongoJTalkSubtitleLine line, InputTextSubCaption enCaption) {
		int startDiff = line.getStartTime() - enCaption.getStart().getMSeconds();
		int endDiff = line.getEndTime() - enCaption.getEnd().getMSeconds();
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
		
		int interStartDiff = enCaption.getEnd().getMSeconds() - line.getStartTime();
		int interEndDiff = line.getEndTime() - enCaption.getStart().getMSeconds();
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
