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

import java.util.ArrayList;
import java.util.List;

import com.github.riccardove.easyjasub.SubtitleItem.Inner;

class SubtitleItemElementsList {

	// TODO: better algorithm to assign furigana to parts of the word
	public List<SubtitleItem.Inner> createElementsList(String text,
			String furigana, SubtitleItem item) {

		ArrayList<SubtitleItem.Inner> list = new ArrayList<SubtitleItem.Inner>();
		String kanjiChars = null;
		String chars = null;
		boolean hasKanji = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!JapaneseChar.isSmallSizeJapaneseChar(c) || c == '々'
					|| furigana.indexOf(c) < 0) {
				// assumes this is Kanji chars, 々 is considered part of the
				// kanji; It's a repetition kanji or
				// "ideographic iteration mark", it means that the kanji just
				// before should be repeated.
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
			furiganaToElements(new StringBuilder(furigana), list);
			return list;
		}
		return null;
	}

	private void furiganaToElements(StringBuilder furigana, List<Inner> list) {
		Inner first = list.get(0);
		if (list.size() == 1) {
			// special case, only one element
			if (first.getText() == null) {
				first.setText(furigana.toString());
			}
		} else {
			if (first.getText() != null) {
				furiganaToElements(removeFuriganaAtStart(furigana, first),
						sublistExcludingStart(list));
			} else {
				Inner last = list.get(list.size() - 1);
				if (last.getText() != null) {
					furiganaToElements(removeFuriganaAtEnd(furigana, last),
							sublistExcludingEnd(list));
				} else if (list.size() > 2) {
					Inner second = list.get(1);
					if (second.getText() == null) {
						throw new IllegalArgumentException(
								"It seems there are two subsequent kanji elements in list");
					} else {
						int index = furigana.indexOf(second.getText());
						if (index >= 0) {
							first.setText(furigana.substring(0, index));
							furiganaToElements(
									removeFuriganaTwoFirstItems(furigana,
											second, index),
									sublistExcludingTwoItemsAtStart(list));
						} else {
							throw new IllegalArgumentException(
									"Could not find " + second.getText()
											+ " in " + furigana);
						}
					}
				} else {
					throw new IllegalArgumentException(
							"It seems there is some error in list");
				}
			}
		}
	}

	private StringBuilder removeFuriganaTwoFirstItems(StringBuilder furigana,
			Inner second, int index) {
		return furigana.replace(0, index + second.getText().length(), "");
	}

	private List<Inner> sublistExcludingTwoItemsAtStart(List<Inner> list) {
		return list.subList(2, list.size());
	}

	private List<Inner> sublistExcludingEnd(List<Inner> list) {
		return list.subList(0, list.size() - 1);
	}

	private StringBuilder removeFuriganaAtEnd(StringBuilder furigana, Inner last) {
		return furigana.replace(furigana.length() - last.getText().length(),
				furigana.length(), "");
	}

	private List<Inner> sublistExcludingStart(List<Inner> list) {
		return list.subList(1, list.size());
	}

	private StringBuilder removeFuriganaAtStart(StringBuilder furigana,
			Inner item) {
		return furigana.replace(0, item.getText().length(), "");
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
