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
