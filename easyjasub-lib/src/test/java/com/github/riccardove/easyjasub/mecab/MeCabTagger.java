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


import org.chasen.mecab.Tagger;

public class MeCabTagger {

	protected MeCabTagger(Tagger tagger) {
		this.tagger = tagger;
	}

	private final Tagger tagger;

	public boolean parse(MeCabLattice lattice) {
		return tagger.parse(lattice.getLattice());
	}

	@Override
	protected void finalize() throws Throwable {
		tagger.delete();
		super.finalize();
	}
}
