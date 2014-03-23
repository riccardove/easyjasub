package com.github.riccardove.easyjasub.nvi18n;

/*
 * #%L
 * easyjasub-cmd
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


import java.util.HashSet;

import com.neovisionaries.i18n.LanguageCode;

public final class NvI18NLanguageCode {

	static {
		HashSet<String> set = new HashSet<String>();
		for (LanguageCode l : LanguageCode.values()) {
			set.add(l.toString());
			set.add(l.getAlpha3().toString());
		}
		codes = set;
	}

	private NvI18NLanguageCode() {

	}

	private static final HashSet<String> codes;

	public static boolean isLanguageCode(String code) {
		return codes.contains(code);
	}

}
