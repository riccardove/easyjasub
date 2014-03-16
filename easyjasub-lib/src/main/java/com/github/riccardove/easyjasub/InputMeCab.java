package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-lib
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import com.github.riccardove.easyjasub.kurikosu.Kurikosu;
import com.github.riccardove.easyjasub.kurikosu.KurikosuWord;
import com.github.riccardove.easyjasub.mecab.MeCabGrammarElement;
import com.github.riccardove.easyjasub.mecab.MeCabParser;
import com.github.riccardove.easyjasub.mecab.MeCabRunner;
import com.github.riccardove.easyjasub.mecab.MeCabSubtitleLine;
import com.github.riccardove.easyjasub.mecab.MeCabSubtitleLineItem;
import com.github.riccardove.easyjasub.mecab.MeCabSubtitleList;

public class InputMeCab {

	private final EasyJaSubObserver observer;
	private final String meCabCommand;
	private final MeCabGrammarElement grammarElements;

	public InputMeCab(EasyJaSubObserver observer, String meCabCommand) {
		this.observer = observer;
		this.meCabCommand = meCabCommand;
		grammarElements = new MeCabGrammarElement();
	}

	private List<String> runMeCab(SubtitleList subs, File meCabOutputFile)
			throws EasyJaSubException {
		MeCabRunner runner = null;
		try {
			runner = new MeCabRunner(meCabCommand, subs.size(), meCabOutputFile);
			runner.start();
			for (SubtitleLine line : subs) {
				if (line.getJapaneseSubKey() != null) {
					observer.onMeCabInputLine();
					runner.append(line.getJapanese());
				}
			}
			runner.close();
		} catch (IOException e) {
			throw new EasyJaSubException(
					"There was an error running MeCab from " + meCabCommand
							+ ": " + e.getMessage(), e);
		} catch (InterruptedException e) {
			throw new EasyJaSubException(
					"MeCab process terminated unexpectedly: " + e.getMessage(),
					e);
		}
		if (runner.getErrorLines().size() > 0) {
			EasyJaSubStringBuilder message = new EasyJaSubStringBuilder();
			message.appendLine("There where errors in MeCab output: ");
			for (String errorLine : runner.getErrorLines()) {
				message.appendLine(errorLine);
			}
			throw new EasyJaSubException(message.toString());
		}
		return runner.getOutputLines();
	}

	public void run(SubtitleList subs, File meCabOutputFile)
			throws EasyJaSubException {

		List<String> meCabOutput;
		if (meCabOutputFile.exists()) {
			meCabOutput = new ArrayList<String>();
			try {
				EasyJaSubReader reader = new EasyJaSubReader(meCabOutputFile);
				for (String line = reader.readLine(); line != null; line = reader
						.readLine()) {
					meCabOutput.add(line);
				}
			} catch (IOException e) {
				throw new EasyJaSubException(
						"Error while reading MeCab output file "
								+ meCabOutputFile.getAbsolutePath());
			}
			observer.onMeCabFileRed(meCabOutputFile, meCabOutput);
		} else {
			meCabOutput = runMeCab(subs, meCabOutputFile);
			observer.onMeCabExecuted(meCabOutputFile, meCabOutput);
		}

		MeCabSubtitleList meCabList = new MeCabParser().parse(meCabOutput,
				observer);
		int size = meCabList.size();
		int subsIndex = 0;
		int subsSize = subs.size();
		observer.onMeCabParsed(size);
		HashSet<String> unknownGrammar = new HashSet<String>();
		List<String> pronunciationErrors = new ArrayList<String>();
		for (int meCabIndex = 0; meCabIndex < size && subsIndex < subsSize; ++meCabIndex) {
			SubtitleLine line;
			do {
				line = subs.get(subsIndex);
				subsIndex++;
			} while (line != null && line.getJapaneseSubKey() == null
					&& subsIndex < subsSize);
			if (line == null || line.getJapaneseSubKey() == null
					|| subsIndex > subsSize) {
				break;
			}

			MeCabSubtitleLine meCabLine = meCabList.get(meCabIndex);
			ArrayList<SubtitleItem> items = new ArrayList<SubtitleItem>();
			for (MeCabSubtitleLineItem meCabItem : meCabLine) {
				SubtitleItem subsItem = new SubtitleItem();

				Grammar grammar = grammarElements.translate(meCabItem
						.getGrammarElement());
				if (grammar == Grammar.undef) {
					unknownGrammar.add(meCabItem.getGrammarElement());
				}
				String text = meCabItem.getText();
				String reading = meCabItem.getReading();
				if (reading != null && canHaveFurigana(grammar)) {
					KurikosuWord word = null;
					try {
						word = Kurikosu.convertKatakanaToHiragana(reading);
					} catch (EasyJaSubException e) {
						pronunciationErrors.add(e.getMessage());
					}
					if (word != null) {
						if (!word.getHiragana().equals(text)) {
							trySetFurigana(text, word.getHiragana(), subsItem);
						}
						subsItem.setRomaji(word.getRomaji());
					}
				}

				subsItem.setText(meCabItem.getText());
				items.add(subsItem);
			}
			if (items.size() > 0) {
				line.setItems(items);
			}
		}
		if (unknownGrammar.size() > 0 || pronunciationErrors.size() > 0) {
			observer.onMeCabUnknownGrammar(new TreeSet<String>(unknownGrammar),
					pronunciationErrors);
		}
	}

	private static boolean canHaveFurigana(Grammar grammar) {
		switch (grammar) {
		case punctuation:
		case symbol:
			return false;
		default:
			return true;
		}
	}

	private void trySetFurigana(String text, String furigana, SubtitleItem item) {

		ArrayList<SubtitleItem.Inner> list = new ArrayList<SubtitleItem.Inner>();
		String kanjiChars = null;
		String chars = null;
		boolean hasKanji = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!JapaneseChar.isSmallSizeJapaneseChar(c)) {
				hasKanji = true;
				if (chars != null) {
					addText(list, chars);
					chars = null;
				}
				if (kanjiChars == null) {
					kanjiChars = Character.toString(c);
				} else {
					kanjiChars += c;
				}
			} else {
				if (kanjiChars != null) {
					addKanji(list, kanjiChars);
					kanjiChars = null;
				}
				if (chars == null) {
					chars = Character.toString(c);
				} else {
					chars += c;
				}
			}
		}
		if (kanjiChars != null) {
			addKanji(list, kanjiChars);
		}
		if (chars != null) {
			addText(list, chars);
		}
		if (hasKanji) {
			item.setElements(list);
			item.setFurigana(furigana);
		}
	}

	private void addText(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setText(chars);
		list.add(inner);
	}

	private void addKanji(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setKanji(chars);
		list.add(inner);
	}
}
