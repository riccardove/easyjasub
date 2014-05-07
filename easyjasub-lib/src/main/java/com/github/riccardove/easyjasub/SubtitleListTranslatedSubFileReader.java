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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;

class SubtitleListTranslatedSubFileReader {

	private final int msecondsMatch;
	private final int msecondsApproxMatch;

	public SubtitleListTranslatedSubFileReader(int msecondsMatch,
			int msecondsApproxMatch) {
		this.msecondsMatch = msecondsMatch;
		this.msecondsApproxMatch = msecondsApproxMatch;
	}

	public void readTranslationSubtitles(SubtitleList s, File file,
			SubtitleFileType type, EasyJaSubObserver observer,
			EasyJaSubLinesSelection selection, boolean showTranslation)
			throws IOException, InputTextSubException {
		InputTextSubFile subs = getInputTextSub(file, type);
		s.setTranslationTitle(subs.getTitle());
		s.setTranslatedSubDescription(subs.getDescription());
		s.setTranslatedSubLanguage(subs.getLanguage());
		s.setTranslatedSubWarnings(subs.getWarnings());
		for (InputTextSubCaption translatedCaption : subs.getCaptions()) {
			String content = SubtitleListJapaneseSubFileReader
					.getContent(translatedCaption);
			if (content != null) {
				SubtitleTranslatedLine translatedLine = createTranslatedLine(
						translatedCaption, content);
				addTranslationToList(s, observer, selection, showTranslation,
						translatedLine);
			}
		}
		copyPreviousTranslationWhenNone(s);
	}

	private void copyPreviousTranslationWhenNone(SubtitleList s) {
		String lastTranslation = null;
		for (SubtitleLine jaLine : s) {
			if (isJa(jaLine)) {
				if (!isTranslation(jaLine)) {
					if (lastTranslation != null) {
						jaLine.setTranslatedText(lastTranslation);
					}
				} else {
					lastTranslation = jaLine.getTranslation();
				}
			} else {
				lastTranslation = null;
			}
		}
	}

	private SubtitleTranslatedLine createTranslatedLine(
			InputTextSubCaption translatedCaption, String content) {
		SubtitleTranslatedLine translatedLine = new SubtitleTranslatedLine();
		String text = replaceNewlines(content);
		translatedLine.setText(text);
		translatedLine.setStartTime(translatedCaption.getStart()
				.getMSeconds());
		translatedLine.setEndTime(translatedCaption.getEnd()
				.getMSeconds());
		return translatedLine;
	}

	private InputTextSubFile getInputTextSub(File file, SubtitleFileType type)
			throws FileNotFoundException, InputTextSubException, IOException {
		FileInputStream stream = new FileInputStream(file);
		InputTextSubFile subs = new InputTextSubFile(type, file.getName(),
				stream);
		stream.close();
		return subs;
	}

	private void addTranslationToList(SubtitleList s,
			EasyJaSubObserver observer, EasyJaSubLinesSelection selection,
			boolean showTranslation, SubtitleTranslatedLine translatedLine) {
		boolean added = false;
		for (SubtitleLine jaLine : s) {
			if (isJa(jaLine) && compatibleWith(jaLine, translatedLine)) {
				addTranslation(jaLine, translatedLine);
				added = true;
			} else if (added) {
				break;
			}
		}
		if (!added) {
			for (SubtitleLine jaLine : s) {
				if (isJa(jaLine)
						&& approxCompatibleWith(jaLine, translatedLine)) {
					addTranslation(jaLine, translatedLine);
					if (!added) {
						added = true;
					} else {
						observer.onTranslatedSubDuplicated(
								translatedLine.getText(),
								translatedLine.getStartTime(),
								jaLine.getStartTime());
					}
				} else if (added) {
					break;
				}
			}
		}
		if (!added && showTranslation) {
			insertTranslation(s, selection, translatedLine);
		}
	}

	private static void addTranslation(SubtitleLine jaLine,
			SubtitleTranslatedLine translatedLineText) {
		String translation = jaLine.getTranslation();
		String text = translatedLineText.getText();
		if (translation == null) {
			translation = text;
		} else {
			translation += BreakStr + text;
		}
		jaLine.setTranslatedText(translation);
	}

	private String replaceNewlines(String content) {
		return TranslationReplace.matcher(content).replaceAll(" ");
	}

	private static final Pattern TranslationReplace = Pattern
			.compile("<br ?/>");
	private static final String BreakStr = "  ";

	private boolean startsAfter(SubtitleLine line,
			SubtitleTranslatedLine translatedCaption) {
		return line.getStartTime() >= translatedCaption.getEndTime();
	}

	private boolean endsBefore(SubtitleLine line,
			SubtitleTranslatedLine translatedCaption) {
		return line.getEndTime() <= translatedCaption.getStartTime();
	}

	private void insertTranslation(SubtitleList lines,
			EasyJaSubLinesSelection selection, SubtitleTranslatedLine content) {
		// TODO: avoid inserting lines that match a non-japanese line with just
		// getSubText()
		for (int i = 0; i < lines.size(); ++i) {
			SubtitleLine line = lines.get(i);
			if (isJa(line) && startsAfter(line, content)) {
				if (i > 0 && !endsBefore(lines.get(i - 1), content)) {
					addTranslation(lines.get(i - 1), content);
				} else {
					if (selection == null
							|| isTimeCompatibleWithSelection(content, selection)) {
						SubtitleLine translationLine = lines.add(i);
						SubtitleLine previous = i > 0 ? lines.get(i - 1) : null;
						SubtitleLine next = null;
						if (previous != null) {
							previous.setNext(translationLine);
							translationLine.setPrevious(previous);
							next = previous.getNext();
						}
						if (next == null && lines.size() > i + 1) {
							next = lines.get(i + 1);
						}
						if (next != null) {
							next.setPrevious(translationLine);
							translationLine.setNext(next);
						}
						translationLine.setStartTime(content.getStartTime());
						translationLine.setEndTime(content.getEndTime());
						addTranslation(translationLine, content);
					}
				}
				break;
			}
		}

	}

	private boolean isTimeCompatibleWithSelection(
			SubtitleTranslatedLine translatedCaption,
			EasyJaSubLinesSelection selection) {
		if (selection.getEndTime() > 0
				&& translatedCaption.getStartTime() > selection.getEndTime()) {
			return false;
		}
		if (selection.getStartTime() > 0
				&& translatedCaption.getEndTime() < selection.getStartTime()) {
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

	private boolean compatibleWith(SubtitleLine line,
			SubtitleTranslatedLine translatedCaption) {
		int startDiff = line.getStartTime() - translatedCaption.getStartTime();
		int endDiff = line.getEndTime() - translatedCaption.getEndTime();
		// both start and end of the line are nearby
		return (Math.abs(startDiff) < msecondsMatch || Math.abs(endDiff) < msecondsMatch);
	}

	private boolean approxCompatibleWith(SubtitleLine line,
			SubtitleTranslatedLine translatedCaption) {
		// TODO: avoid that captions that have end time near the start of the
		// subsequent caption are evaluated for match at the end
		int startDiff = line.getStartTime() - translatedCaption.getStartTime();
		int endDiff = line.getEndTime() - translatedCaption.getEndTime();
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

		int interStartDiff = translatedCaption.getEndTime()
				- line.getStartTime();
		int interEndDiff = line.getEndTime() - translatedCaption.getStartTime();
		// ja: -------************----------------------
		// en: ------------***************--------------
		if (startDiff < 0 && endDiff < 0 && interEndDiff > msecondsApproxMatch) {
			// japanese line starts before translation line but they share
			// 0.5 seconds
			return true;
		}
		// ja: ------------***************--------------
		// en: -------************----------------------
		if (startDiff > 0 && endDiff > 0
				&& interStartDiff > msecondsApproxMatch) {
			// japanese line starts after translation line but they share 0.5
			// seconds
			return true;
		}
		return false;
	}

}
