package com.github.riccardove.easyjasub.jmdict;

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

final class JMDictSense implements IJMDictSense {
	public JMDictSense() {
		posList = new ArrayList<String>();
		glossList = new ArrayList<String>();
	}

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

	protected void clear() {
		glossList.clear();
		posList.clear();
	}
}
