package com.github.riccardove.easyjasub.mecab;

import java.util.List;

class MeCabParser {

	public MeCabSubtitleList parse(List<String> text) {
		MeCabSubtitleList list = new MeCabSubtitleList();
		MeCabSubtitleLine line = null;
		for (String textLine : text) {
			if ("EOS".equals(textLine)) {
				line = null;
			} else {
				MeCabLineMatcher matcher = new MeCabLineMatcher(textLine);
				if (!matcher.matches()) {
					// invalid line
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
