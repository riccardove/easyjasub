package com.github.riccardove.easyjasub;

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


import java.util.List;

import com.github.riccardove.easyjasub.kurikosu.Kurikosu;
import com.github.riccardove.easyjasub.lucene.LuceneUtil;

class EasyJaSubJapaneseTextConverter {

	public static EasyJaSubJapaneseConvertedText convertKatakanaToHiragana(String katakana,
			List<String> pronunciationErrors) {
		if (!Kurikosu.isKatakana(katakana)) {
			return null;
		}
		try {
			EasyJaSubJapaneseConvertedText word = new EasyJaSubJapaneseConvertedText();
			word.setHiragana(Kurikosu.convertKatakanaToHiragana(katakana));
			word.setRomaji(convertToRomaji(katakana));
			return word;
		} catch (EasyJaSubException e) {
			pronunciationErrors.add(e.getMessage());
			return null;
		}
	}

	/**
	 * Convert katakana to romaji with Lucene converter if Kurikosu throws
	 * errors
	 */
	private static String convertToRomaji(String katakana)
			throws EasyJaSubException {
		try {
			return Kurikosu.convertKatakanaToRomaji(katakana);
		} catch (EasyJaSubException ex) {
			try {
				return LuceneUtil.KatakanaToRomaji(katakana);
			} catch (Throwable luceneEx) {
				throw ex;
			}
		}
	}

}
