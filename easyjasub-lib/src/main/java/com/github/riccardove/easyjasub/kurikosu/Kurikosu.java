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


import org.kurikosu.lang.Katakana;
import org.kurikosu.transcription.Katakana2Romaji;

import com.github.riccardove.easyjasub.EasyJaSubException;

public final class Kurikosu {

	public static KurikosuWord convertKatakanaToHiragana(String katakana)
			throws EasyJaSubException {
		if (!Katakana.isKatakana(katakana)) {
			return null;
		}
		try {
			KurikosuWord word = new KurikosuWord();
			Katakana k = new Katakana(katakana);
			word.setRomaji(new Katakana2Romaji().transcribe(k).getValue());
			word.setHiragana(new Katakana2Hiragana().transcribe(k).getValue());
			return word;
		} catch (Throwable e) {
			throw new EasyJaSubException("Error converting \"" + katakana
					+ "\": " + e.getMessage(), e);
		}
	}
}
