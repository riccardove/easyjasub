package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.EasyJaSubDictionaryEntry.Sense;
import com.github.riccardove.easyjasub.jmdict.IJMDictSense;
import com.github.riccardove.easyjasub.jmdict.JMDictObserver;
import com.github.riccardove.easyjasub.jmdict.JMDictParser;

/**
 * Dictionary implementation, capable to retrieve a list of possible
 * translations given a word.
 * 
 * Can be serialized, populating this class with all possible entries may be
 * expensive
 */
class EasyJaSubDictionary implements Serializable {

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
		EasyJaSubTrie.Value<EasyJaSubDictionaryEntry> value = trie
				.get(new CharacterIterator(word));
		if (value == null) {
			return null;
		}
		return value.getValue();
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
		new JMDictParser().parse(file, new JMDictParserObserver(),
				threeLetterlanguageCode);
	}

	private final class JMDictParserObserver implements JMDictObserver {
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
				value.setValue(entry);
			}
			return entry;
		}

		private List<EasyJaSubDictionaryEntry.Sense> getSenseList(
				Collection<IJMDictSense> senses) {
			List<EasyJaSubDictionaryEntry.Sense> senseList = new ArrayList<EasyJaSubDictionaryEntry.Sense>();
			for (IJMDictSense sense : senses) {
				EasyJaSubDictionaryEntry.Sense s = new Sense();
				for (String pos : sense.getPartOfSpeech()) {
					s.addPartOfSpeech(pos);
				}
				for (String gloss : sense.getGloss()) {
					s.addGloss(gloss);
				}
				senseList.add(s);
			}
			return senseList;
		}
	}

}
