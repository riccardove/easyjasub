package com.github.riccardove.easyjasub.mecab;

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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MeCabLineMatcher {

	public MeCabLineMatcher(String line) {
		matcher = Regex.matcher(line);
		if (!matcher.matches()) {
			matcher = Regex2.matcher(line);
			second = true;
		}
	}

	/**
	 * Parses a line of MeCab output, e.g.:<br/>
	 * 魔法 名詞,一般,*,*,*,*,魔法,マホウ,マホー<br/>
	 */
	private static final String RegexStr = "^([^\t]+)\t([^,]+),[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,(.+)$";
	/**
	 * lines for proper nouns seems to be different:<br/>
	 * フィオーレ 名詞,固有名詞,一般,*,*,*,* <br/>
	 */
	private static final String RegexStr2 = "^([^\\s]+)\\s([^,]+),[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+$";
	private static final Pattern Regex = Pattern.compile(RegexStr);
	private static final Pattern Regex2 = Pattern.compile(RegexStr2);
	private Matcher matcher;
	private boolean second;

	public boolean matches() {
		return matcher.matches();
	}

	public String originalWord() {
		return matcher.group(1);
	}

	/**
	 * Grammar element, in Japanese, for example:
	 * <ul>
	 * <li>魔法: noun</li>
	 * <li>助詞: particle</li>
	 * <li>記号: symbol</li>
	 * <li>動詞: verb</li>
	 * <li>助動詞: auxiliary verb</li>
	 * </ul>
	 */
	public String grammar() {
		return matcher.group(2);
	}

	public String katakana() {
		if (second) {
			return null;
		}
		return matcher.group(3);
	}
}
