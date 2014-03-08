package com.github.riccardove.easyjasub.mecab;

import java.io.IOException;

import com.github.riccardove.easyjasub.EasyJaSubException;
import com.github.riccardove.easyjasub.EasyJaSubObserver;
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

	public void run(SubtitleList subs) throws EasyJaSubException {
		MeCabRunner runner = null;
		try {
			runner = new MeCabRunner(meCabCommand, subs.size());
			for (SubtitleLine line : subs) {
				if (line.getJapaneseSubKey() != null) {
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

		MeCabSubtitleList meCabList = new MeCabParser().parse(runner
				.getOutputLines());
		int size = meCabList.size();
		int subsIndex = 0;
		int subsSize = subs.size();
		for (int meCabIndex = 0; meCabIndex < size && subsIndex < subsSize; ++meCabIndex) {
			SubtitleLine line;
			do {
				line = subs.get(subsIndex);
				subsIndex++;
			} while (line != null && line.getJapaneseSubKey() == null
					&& subsIndex < subsSize);

			MeCabSubtitleLine meCabLine = meCabList.get(meCabIndex);
			for (MeCabSubtitleLineItem meCabItem : meCabLine) {
				SubtitleItem subsItem = new SubtitleItem();

				Grammar grammar = grammarElements.translate(meCabItem
						.getGrammarElement());

				String furigana = katakanaToHiragana(meCabItem.getReading());
				if (canHaveFurigana(grammar)
						&& !furigana.equals(meCabItem.getText())) {
					subsItem.setFurigana(furigana);
				}

				subsItem.setText(meCabItem.getText());
			}
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
