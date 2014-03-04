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


import org.chasen.mecab.Model;

public class MeCabModel {

	protected MeCabModel(String args) {
		if (args == null) {
			model = new Model();
		} else {
			model = new Model(args);
		}
	}

	private final Model model;

	public MeCabLattice createLattice() {
		return new MeCabLattice(model.createLattice());
	}

	public MeCabTagger createTagger() {
		return new MeCabTagger(model.createTagger());
	}

	@Override
	protected void finalize() throws Throwable {
		model.delete();
		super.finalize();
	}
}
