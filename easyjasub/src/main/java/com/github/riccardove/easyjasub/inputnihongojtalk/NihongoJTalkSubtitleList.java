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


import java.util.ArrayList;
import java.util.Iterator;

class NihongoJTalkSubtitleList implements Iterable<NihongoJTalkSubtitleLine> {

	public NihongoJTalkSubtitleList() {
		lines = new ArrayList<NihongoJTalkSubtitleLine>();
	}

	private ArrayList<NihongoJTalkSubtitleLine> lines;
	
	public NihongoJTalkSubtitleLine add() {
		NihongoJTalkSubtitleLine line = new NihongoJTalkSubtitleLine();
		lines.add(line);
		return line;
	}
	
	public NihongoJTalkSubtitleLine get(int index) {
		while (lines.size() <= index) {
			add();
		}
		return lines.get(index);
	}

	public Iterator<NihongoJTalkSubtitleLine> iterator() {
		return lines.iterator();
	}
	
	public int size() {
		return lines.size();
	}
}
