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

import java.util.ArrayList;
import java.util.Iterator;

public class MeCabSubtitleLine implements Iterable<MeCabSubtitleLineItem> {

	private final ArrayList<MeCabSubtitleLineItem> items;

	public MeCabSubtitleLine() {
		items = new ArrayList<MeCabSubtitleLineItem>();
	}

	public void addItem(MeCabSubtitleLineItem item) {
		items.add(item);
	}

	@Override
	public Iterator<MeCabSubtitleLineItem> iterator() {
		return items.iterator();
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		for (MeCabSubtitleLineItem item : items) {
			text.append(item.toString());
			text.append(" ");
		}
		return text.toString();
	}
}
