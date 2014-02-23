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


import java.util.HashSet;
import java.util.Set;

public enum SubtitleFileType {
	Undef,
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
