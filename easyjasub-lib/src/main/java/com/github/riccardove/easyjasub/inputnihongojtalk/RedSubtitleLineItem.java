package com.github.riccardove.easyjasub.inputnihongojtalk;

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
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.PartOfSpeech;
import com.github.riccardove.easyjasub.SubtitleItem;

class RedSubtitleLineItem  extends TextSubtitleLineItem {

	public RedSubtitleLineItem(NihongoJTalkSubtitleLine subtitleLine, String text, 
			String pos, String romaji)  
	{
		super(subtitleLine, text);
		if (pos == null) {
			throw new NullPointerException("Invalid pos for text " + text);
		}
		if (romaji == null) {
			throw new NullPointerException("Invalid romaji for text " + text);
		}
		this.pos= pos;
		this.romaji= romaji;
		posc = PosRegex.matcher(pos).replaceAll("").replace(" ", "");
		PosSet.add(posc);
		gpos = Enum.valueOf(PartOfSpeech.class, posc);
	}
	
	private static final Pattern PosRegex = Pattern.compile("[\\.()-]");
	public static final HashSet<String> PosSet = new HashSet<String>();
	protected final PartOfSpeech gpos;
	protected String pos;
	protected String romaji;
	protected String posc;

	@Override
	public void toItem(SubtitleItem item) {
		item.setRomaji(romaji);
		item.setPartOfSpeech(posc);
		super.toItem(item);
	}
	
	@Override
	public String toString() {
		return super.toString() + "{" + pos + "}(" + romaji + ")";
	}
}
