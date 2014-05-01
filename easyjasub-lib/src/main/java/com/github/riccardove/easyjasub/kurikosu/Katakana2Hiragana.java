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

/**
 * Small extension to Kurikosu to convert from Katakana to Hiragana
 */
public class Katakana2Hiragana {

	private static final int HIRAGANA_KATAKANA_UNICODE_SHIFT = 6 * 16;

	public Hiragana transcribe(Katakana katakana) {
		final String katakanaValue = katakana.getValue();
		String hiraganaValue = "";

		for (int index = 0; index < katakanaValue.length(); index++) {
			final char hiraganaCharacter = katakanaValue.charAt(index);
			if (hiraganaCharacter == 'ー') {
				hiraganaValue += hiraganaCharacter;
			} else {
				final char katakanaCharacter = (char) ((hiraganaCharacter) - HIRAGANA_KATAKANA_UNICODE_SHIFT);
				hiraganaValue += katakanaCharacter;
			}
		}
		return new Hiragana(hiraganaValue);
	}
}
