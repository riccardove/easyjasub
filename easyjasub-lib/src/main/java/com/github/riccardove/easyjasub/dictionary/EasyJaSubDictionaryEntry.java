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
