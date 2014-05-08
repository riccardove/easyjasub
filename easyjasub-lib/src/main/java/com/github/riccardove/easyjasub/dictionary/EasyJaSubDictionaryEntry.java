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

import com.github.riccardove.easyjasub.EasyJaSubWordTranslation;
import com.github.riccardove.easyjasub.EasyJaSubWordTranslationSense;

// TODO
class EasyJaSubDictionaryEntry implements EasyJaSubWordTranslation {

	public EasyJaSubDictionaryEntry() {
		senses = new ArrayList<EasyJaSubWordTranslationSense>();
	}

	private final ArrayList<EasyJaSubWordTranslationSense> senses;

	public void addSense(Sense sense) {
		senses.add(sense);
	}

	@Override
	public Iterable<EasyJaSubWordTranslationSense> getSenses() {
		return senses;
	}

	public static class Sense implements EasyJaSubWordTranslationSense {

		public Sense(int index) {
			posList = new ArrayList<String>();
			glossList = new ArrayList<String>();
			this.index = index;
		}

		private final int index;
		private final ArrayList<String> glossList;
		private final ArrayList<String> posList;

		@Override
		public Iterable<String> getPartOfSpeech() {
			return posList;
		}

		@Override
		public Iterable<String> getGloss() {
			return glossList;
		}

		protected void addPartOfSpeech(String pos) {
			posList.add(pos);
		}

		protected void addGloss(String gloss) {
			glossList.add(gloss);
		}

		@Override
		public int getIndex() {
			return index;
		}
	}

	private int length;

	@Override
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
