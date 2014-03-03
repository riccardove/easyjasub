package com.github.riccardove.easyjasub;

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
}
