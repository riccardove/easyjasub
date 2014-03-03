package com.github.riccardove.easyjasub;

import java.awt.Font;
import java.util.*;

class FontList implements Iterable<String> {

	public FontList() {
		fonts = new TreeMap<String, Font>();
		int[] chars = new int[] {
				0x829F,
				0x88A3,
				0x8353,
		};
		for (Font font : java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
			if (isJapanese(chars, font)) {
				fonts.put(font.getPSName(), font);
			}
		}
	}

	private boolean isJapanese(int[] chars, Font font) {
		for (int japaneseChar : chars) {
			if (!font.canDisplay(japaneseChar)) {
				return false;
			}
		}
		return true;
	}

	private TreeMap<String, Font> fonts;
	
	@Override
	public Iterator<String> iterator() {
		return fonts.keySet().iterator();
	}
}
