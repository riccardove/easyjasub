package com.github.riccardove.easyjasub.mecab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import com.github.riccardove.easyjasub.EasyJaSubException;
import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.EasyJaSubReader;
import com.github.riccardove.easyjasub.EasyJaSubStringBuilder;
import com.github.riccardove.easyjasub.Grammar;
import com.github.riccardove.easyjasub.JapaneseChar;
import com.github.riccardove.easyjasub.SubtitleItem;
import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

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
		for (int meCabIndex = 0; meCabIndex < size && subsIndex < subsSize; ++meCabIndex) {
			SubtitleLine line;
			do {
				line = subs.get(subsIndex);
				subsIndex++;
			} while (line != null && line.getJapaneseSubKey() == null
					&& subsIndex < subsSize);

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
				if (reading != null) {
					String furigana = katakanaToHiragana(reading);
					if (canHaveFurigana(grammar) && !furigana.equals(text)) {
						subsItem.setFurigana(furigana);
						subsItem.setElements(text);
					}
				}

				subsItem.setText(meCabItem.getText());
				items.add(subsItem);
			}
			if (items.size() > 0) {
				line.setItems(items);
			}
		}
		if (unknownGrammar.size() > 0) {
			observer.onMeCabUnknownGrammar(new TreeSet<String>(unknownGrammar));
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

	private static String katakanaToHiragana(String text) {
		int length = text.length();
		StringBuilder result = new StringBuilder(length);
		for (int i = 0; i < length; ++i) {
			result.append(JapaneseChar.katakanaToHiragana(text.charAt(i)));
		}
		return result.toString();
	}

}
