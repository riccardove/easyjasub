package com.github.riccardove.easyjasub.mecab;

import java.util.HashMap;

import com.github.riccardove.easyjasub.Grammar;

class MeCabGrammarElement {

	public MeCabGrammarElement() {
		map = new HashMap<String, Grammar>();
		map.put("魔法", Grammar.noun);
		map.put("助詞", Grammar.particle);
		map.put("記号", Grammar.symbol);
		map.put("動詞", Grammar.verb);
		map.put("助動詞", Grammar.auxiliaryverb);
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
