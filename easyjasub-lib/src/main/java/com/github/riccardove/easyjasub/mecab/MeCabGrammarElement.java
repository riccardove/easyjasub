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


import java.util.HashMap;

import com.github.riccardove.easyjasub.Grammar;

public class MeCabGrammarElement {

	public MeCabGrammarElement() {
		map = new HashMap<String, Grammar>();
		map.put("魔法", Grammar.noun);
		map.put("助詞", Grammar.particle);
		map.put("記号", Grammar.symbol);
		map.put("動詞", Grammar.verb);
		map.put("助動詞", Grammar.auxiliaryverb);

		map.put("フィラー", Grammar.fill);
		map.put("副詞", Grammar.adv);
		map.put("名詞", Grammar.propern);
		map.put("形容詞", Grammar.adj);
		map.put("感動詞", Grammar.interj);
		map.put("接続詞", Grammar.conj);
		map.put("接頭詞", Grammar.prefix);
		map.put("連体詞", Grammar.attributive);
	}

	private final HashMap<String, Grammar> map;

	public Grammar translate(String japaneseText) {
		Object value = map.get(japaneseText);
		if (value == null) {
			return Grammar.undef;
		}
		return (Grammar) value;
	}
}
