package com.github.riccardove.easyjasub.commons;

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


import org.apache.commons.lang3.StringUtils;

/**
 * Usages of org.apache.commons.lang3.StringUtils are wrapped by this class
 */
public abstract class CommonsLangStringUtils {
	public static String join(Iterable<?> list, String separator) {
		return StringUtils.join(list, separator);
	}

	public static String join(Object[] array, String separator) {
		return StringUtils.join(array, separator);
	}

	public static int countMatches(String str, String sub) {
		return StringUtils.countMatches(str, sub);
	}

	public static boolean isEmpty(String title) {
		return StringUtils.isEmpty(title);
	}

	public static String charListToString(Iterable<Character> japaneseChars) {
		return StringUtils.join(japaneseChars, "");
	}

	public static String removeStart(String str, String remove) {
		return StringUtils.removeStart(str, remove);
	}

	public static String removeEnd(String str, String remove) {
		return StringUtils.removeEnd(str, remove);
	}
}
