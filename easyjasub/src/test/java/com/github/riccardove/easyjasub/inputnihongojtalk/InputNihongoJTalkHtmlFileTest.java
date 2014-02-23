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

import junit.framework.TestCase;

import org.junit.Test;

import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

public class InputNihongoJTalkHtmlFileTest extends TestCase {

	@Test
	public void testParse() throws Exception {
		File file = new File("samples\\sample1.htm");
		assertTrue(file.exists());
		SubtitleList s = new SubtitleList("test");
		new InputNihongoJTalkHtmlFile().parse(file, s, new Observer());
		assertTrue(s.size() > 0);
	}

	private class Observer extends FakeEasyJaSubObserver {
		@Override
		public void onInputNihongoJTalkHtmlLine(SubtitleLine line) {
			//System.out.println(line.getIndex() + ": " + line.toString());
		}
	}

}
