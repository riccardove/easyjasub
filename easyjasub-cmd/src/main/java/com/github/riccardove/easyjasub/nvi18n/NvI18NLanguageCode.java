package com.github.riccardove.easyjasub.nvi18n;

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
