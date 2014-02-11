package com.github.riccardove.easyjasub;

import java.util.HashSet;
import java.util.Set;

public enum SubtitleFileType {
	SRT,
	STL,
	SCC,
	XML,
	ASS, 
	TTML
	;
	
	static {
		HashSet<String> set = new HashSet<String>();
		for (SubtitleFileType value : SubtitleFileType.values()) {
			set.add(value.toString());
		}
		values = set;
	}
	
	private static final Set<String> values;
	
	protected static boolean isValue(String value) {
		return values.contains(value);
	}
	
	protected static Iterable<String> getValuesSet() {
		return values;
	}
}
