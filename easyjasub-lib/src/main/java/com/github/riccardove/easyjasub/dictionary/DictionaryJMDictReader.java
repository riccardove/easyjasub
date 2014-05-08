package com.github.riccardove.easyjasub.dictionary;

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
import java.util.Collection;
import java.util.List;

import com.github.riccardove.easyjasub.CharacterIterator;
import com.github.riccardove.easyjasub.EasyJaSubTrie;
import com.github.riccardove.easyjasub.dictionary.EasyJaSubDictionaryEntry.Sense;
import com.github.riccardove.easyjasub.jmdict.IJMDictSense;
import com.github.riccardove.easyjasub.jmdict.JMDictObserver;

final class DictionaryJMDictReader implements JMDictObserver {

	public DictionaryJMDictReader(
			EasyJaSubTrie<EasyJaSubDictionaryEntry, Character> trie,
			ArrayList<String> errors) {
		this.trie = trie;
		this.errors = errors;
	}

	private final EasyJaSubTrie<EasyJaSubDictionaryEntry, Character> trie;
	private final ArrayList<String> errors;

	@Override
	public void onError(int index, String entseq, String message) {
		errors.add("JMDict error " + index + " " + entseq + " " + message);
	}

	@Override
	public void onEntry(int index, String entseq, String keb, String reb,
			Collection<IJMDictSense> senses) {
		List<EasyJaSubDictionaryEntry.Sense> senseList = getSenseList(senses);
		addEntry(keb, senseList);
		addEntry(reb, senseList);
	}

	private void addEntry(String keb,
			List<EasyJaSubDictionaryEntry.Sense> senseList) {
		if (keb != null) {
			EasyJaSubDictionaryEntry entry = getEntry(keb);
			for (Sense sense : senseList) {
				entry.addSense(sense);
			}
		}
	}

	private EasyJaSubDictionaryEntry getEntry(String keb) {
		EasyJaSubTrie.Value<EasyJaSubDictionaryEntry> value = trie
				.add(new CharacterIterator(keb));
		EasyJaSubDictionaryEntry entry = value.getValue();
		if (entry == null) {
			entry = new EasyJaSubDictionaryEntry();
			entry.setLength(keb.length());
			value.setValue(entry);
		}
		return entry;
	}

	private List<EasyJaSubDictionaryEntry.Sense> getSenseList(
			Collection<IJMDictSense> senses) {
		List<EasyJaSubDictionaryEntry.Sense> senseList = new ArrayList<EasyJaSubDictionaryEntry.Sense>();
		int index = 0;
		for (IJMDictSense sense : senses) {
			EasyJaSubDictionaryEntry.Sense s = new Sense(index++);
			boolean glossFound = false;
			for (String gloss : sense.getGloss()) {
				// tries to simplify the gloss to fit a subtitle word
				gloss = DictionaryGloss.choose(gloss, "             "); // TODO
				if (gloss != null) {
					glossFound = true;
					s.addGloss(gloss);
				}
			}
			if (!glossFound) {
				String gloss = DictionaryGloss.getLong(sense.getGloss()
						.iterator().next());
				s.addGloss(gloss);
			}
			for (String pos : sense.getPartOfSpeech()) {
				s.addPartOfSpeech(pos);
			}
			senseList.add(s);
		}
		return senseList;
	}
}