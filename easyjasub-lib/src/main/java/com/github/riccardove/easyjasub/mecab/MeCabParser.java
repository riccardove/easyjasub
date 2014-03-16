package com.github.riccardove.easyjasub.mecab;

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


import java.util.List;

import com.github.riccardove.easyjasub.EasyJaSubObserver;

public class MeCabParser {

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
