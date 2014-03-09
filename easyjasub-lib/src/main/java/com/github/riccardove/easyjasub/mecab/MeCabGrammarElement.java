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
