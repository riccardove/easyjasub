package com.github.riccardove.easyjasub.mecab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MeCabLineMatcher {

	public MeCabLineMatcher(String line) {
		matcher = Regex.matcher(line);
	}

	/**
	 * Parses a line of MeCab output, e.g.:<br/>
	 * 魔法 名詞,一般,*,*,*,*,魔法,マホウ,マホー
	 */
	private static final String RegexStr = "(^[^\t]+)\t([^,]+),[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,(.+)$";
	private static final Pattern Regex = Pattern.compile(RegexStr);
	private final Matcher matcher;

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
		return matcher.group(3);
	}
}
