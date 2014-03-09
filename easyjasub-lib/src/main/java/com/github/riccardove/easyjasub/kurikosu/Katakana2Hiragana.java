/**
 * 
 */
package com.github.riccardove.easyjasub.kurikosu;

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

import org.kurikosu.lang.Hiragana;
import org.kurikosu.lang.Katakana;

public class Katakana2Hiragana {

	private static final int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;

	/**
	 * Transcribing Hiragana to Katakana is quiet simple because it can be
	 * done by shifting the unicode of each character by (6*16) positions to the
	 * left.
	 * 
	 * @param katakana
	 * @return
	 */
	public Hiragana transcribe(Katakana katakana) {
		final String hiraganaValue = katakana.getValue();

		String katakanaValue = "";

		for (int index = 0; index < hiraganaValue.length(); index++) {

			final char hiraganaCharacter = hiraganaValue.charAt(index);

			if (hiraganaCharacter == 'ー') {
				/* 
				 * 'ー' might be used in hiragana also, even if long vowels 
				 * usally created by appending the vowel itself 
				 */
				katakanaValue += hiraganaCharacter;
				
			} else {

				final char katakanaCharacter = (char) ((hiraganaCharacter) - HIRAGANA_KATAKANA_UNICODE_SHIFT);

				katakanaValue += katakanaCharacter;
				
			}
		}

		return new Hiragana(katakanaValue);
	}
}
