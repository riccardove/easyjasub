package com.github.riccardove.easyjasub.mecab;

import java.util.List;

import com.github.riccardove.easyjasub.EasyJaSubObserver;

class MeCabParser {

	public MeCabSubtitleList parse(List<String> text, EasyJaSubObserver observer) {
		MeCabSubtitleList list = new MeCabSubtitleList();
		MeCabSubtitleLine line = null;
		int count = 0;
		for (String textLine : text) {
			++count;
			if (MeCabProcess.isLineSeparator(textLine)) {
				line = null;
			} else {
				MeCabLineMatcher matcher = new MeCabLineMatcher(textLine);
				if (!matcher.matches()) {
					observer.onMeCabParseInvalidLine(count, textLine);
				} else {
					if (line == null) {
						line = list.add();
					}
					MeCabSubtitleLineItem item = new MeCabSubtitleLineItem();
					item.setReading(matcher.katakana());
					item.setText(matcher.originalWord());
					item.setGrammarElement(matcher.grammar());
					line.addItem(item);
				}
			}
		}

		return list;
	}
}
