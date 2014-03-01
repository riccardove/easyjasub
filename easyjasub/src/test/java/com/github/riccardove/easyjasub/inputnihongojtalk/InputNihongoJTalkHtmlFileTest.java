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


import java.io.File;

import org.junit.Test;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;
import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleList;

public class InputNihongoJTalkHtmlFileTest extends EasyJaSubTestCase {

	
	@Test
	public void testParse() throws Exception {
		File file = getSampleFile("sample1.htm");

		SubtitleList s = new SubtitleList();
		Observer observer = new Observer();
		new InputNihongoJTalkHtmlFile().parse(file, s, observer);
		assertEquals(0, s.size());
		assertEquals(44, observer.count);
		assertEquals("私の色気は　たった1,000ジュエルか！？", observer.last);
	}

	private class Observer extends FakeEasyJaSubObserver {
		public int count;
		public String last;
		
		@Override
		public void onInputNihongoJTalkHtmlLine(String line) {
			count++;
			last = line;
			//System.out.println(line.getIndex() + ": " + line.toString());
		}
	}

}
