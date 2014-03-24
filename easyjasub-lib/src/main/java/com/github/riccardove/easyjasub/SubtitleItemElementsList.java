package com.github.riccardove.easyjasub;

import java.util.ArrayList;
import java.util.List;

import com.github.riccardove.easyjasub.SubtitleItem.Inner;
import com.github.riccardove.easyjasub.commons.CommonsLangStringUtils;

class SubtitleItemElementsList {

	public List<SubtitleItem.Inner> createElementsList(String text,
			String furigana, SubtitleItem item) {

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
		if (hasKanji) {
			if (chars != null) {
				addText(list, chars);
			}
			// furiganaToElements(furigana, list); TODO
			return list;
		}
		return null;
	}

	private void furiganaToElements(String furigana, List<Inner> list) {
		Inner first = list.get(0);
		if (list.size() == 1) {
			// special case, only one element
			if (first.getText() == null) {
				first.setText(furigana.toString());
			}
		} else {
			String remaining = furigana;
			if (first.getText() != null) {
				remaining = CommonsLangStringUtils.removeStart(remaining,
						first.getText());
				if (remaining.length() < furigana.length()) {
					furiganaToElements(remaining, list.subList(1, list.size()));
					return;
				}
			}
			Inner last = list.get(list.size() - 1);
			if (last.getText() != null) {
				remaining = CommonsLangStringUtils.removeEnd(remaining,
						last.getText());
				if (remaining.length() < furigana.length()) {
					furiganaToElements(remaining,
							list.subList(0, list.size() - 1));
					return;
				}
			}
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
