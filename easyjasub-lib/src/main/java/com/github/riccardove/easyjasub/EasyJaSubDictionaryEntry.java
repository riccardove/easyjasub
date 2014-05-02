package com.github.riccardove.easyjasub;

import java.util.ArrayList;

// TODO
class EasyJaSubDictionaryEntry {

	public EasyJaSubDictionaryEntry() {
		senses = new ArrayList<Sense>();
	}

	private final ArrayList<Sense> senses;

	public void addSense(Sense sense) {
		senses.add(sense);
	}

	public Iterable<Sense> getSenses() {
		return senses;
	}

	public static class Sense {

		public Sense() {
			posList = new ArrayList<String>();
			glossList = new ArrayList<String>();
		}

		private final ArrayList<String> glossList;
		private final ArrayList<String> posList;

		public Iterable<String> getPartOfSpeech() {
			return posList;
		}

		public Iterable<String> getGloss() {
			return glossList;
		}

		protected void addPartOfSpeech(String pos) {
			posList.add(pos);
		}

		protected void addGloss(String gloss) {
			glossList.add(gloss);
		}
	}
}
