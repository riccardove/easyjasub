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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.CharacterIterator;
import com.github.riccardove.easyjasub.EasyJaSubTrie;
import com.github.riccardove.easyjasub.EasyJaSubWordTranslation;
import com.github.riccardove.easyjasub.EasyJaSubWordTranslator;
import com.github.riccardove.easyjasub.jmdict.JMDictParser;

/**
 * Dictionary implementation, capable to retrieve a list of possible
 * translations given a word.
 * 
 * Can be serialized, populating this class with all possible entries may be
 * expensive
 */
public class EasyJaSubDictionary implements Serializable,
		EasyJaSubWordTranslator {

	/**
	 * Generated serial code
	 */
	private static final long serialVersionUID = 119316191928895233L;
	private final String threeLetterlanguageCode;

	public EasyJaSubDictionary(String threeLetterlanguageCode) {
		this.threeLetterlanguageCode = threeLetterlanguageCode;
		trie = new EasyJaSubTrie<EasyJaSubDictionaryEntry, Character>();
		errors = new ArrayList<String>();
	}

	private final EasyJaSubTrie<EasyJaSubDictionaryEntry, Character> trie;
	private final ArrayList<String> errors;

	/**
	 * Returns an entry corresponding to specified word, or to a prefix of it
	 * 
	 * @param word
	 * @return
	 */
	public EasyJaSubDictionaryEntry getEntry(String word) {
		if (word == null || word.length() == 0) {
			throw new RuntimeException("Invalid word");
		}
		EasyJaSubTrie.Value<EasyJaSubDictionaryEntry> value = trie
				.get(new CharacterIterator(word));
		if (value == null) {
			return null;
		}
		return value.getValue();
	}

	@Override
	public EasyJaSubWordTranslation translate(String word) {
		return getEntry(word);
	}

	/**
	 * Adds entries from a JMDict file
	 * 
	 * @param file
	 *            an XML JMDict file
	 * @throws IOException
	 *             when reading file
	 * @throws SAXException
	 *             when reading file
	 */
	public void addJMDict(File file) throws IOException, SAXException {
		new JMDictParser().parse(file,
				new DictionaryJMDictReader(trie, errors),
				threeLetterlanguageCode);
	}

	/**
	 * Adds entries from a JMDict file
	 * 
	 * @param stream
	 *            an XML JMDict file as stream
	 * @throws IOException
	 *             when reading file
	 * @throws SAXException
	 *             when reading file
	 */
	public void addJMDict(InputStream stream) throws IOException, SAXException {
		new JMDictParser().parse(stream, new DictionaryJMDictReader(trie,
				errors), threeLetterlanguageCode);
	}

}
