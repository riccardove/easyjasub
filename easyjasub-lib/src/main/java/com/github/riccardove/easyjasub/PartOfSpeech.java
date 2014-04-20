package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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

public enum PartOfSpeech {
	adj, naadj, prenadj,

	verb, pronoun, noun, conj,

	nsuru, interj, particle, aux, fill,

	givenname, persname, surname, propern,

	vpotential, prefix,

	adv, punctuation, region, symbol, auxiliaryverb, undef,
	/**
	 * These may only occur before nouns, not in a predicative position. <br/>
	 * They are various in derivation and word class, and are generally analyzed
	 * as variants of more basic classes, where this specific form (possibly a
	 * fossil) can only be used in restricted settings. <br/>
	 * For example, ōkina (大きな) "big" (variant of 大きい): 大きな事(Ōkina koto)
	 * ("a big thing")<br/>
	 * from Wikipedia
	 * http://en.wikipedia.org/wiki/Japanese_equivalents_of_adjectives
	 */
	attributive,
}
