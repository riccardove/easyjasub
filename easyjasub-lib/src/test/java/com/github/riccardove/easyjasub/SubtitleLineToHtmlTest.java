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


import java.io.IOException;
import java.util.Arrays;

import org.junit.Ignore;

@Ignore
public class SubtitleLineToHtmlTest extends EasyJaSubTestCase {

	private static final String Css = "default.css";

	public void test() throws IOException 
	{
		SubtitleLine line = getSampleLine1();
		String text = new SubtitleLineToHtml(true, true, false, false, true,
				false).toHtml(line, Css);
		assertEquals("test", text);
	}

	public void test2() throws IOException {
		SubtitleLine line = getSampleLine1();
		String text = new SubtitleLineToHtml(true, true, false, false, true,
				false).toHtml(line, Css);
		assertEquals("test", text);
	}

	private SubtitleLine getSampleLine1() {
		SubtitleLine line = new SubtitleLine();
		line.setTranslatedText("the translation");
		line.setSubText("the sub text");
		
		SubtitleItem item1 = new SubtitleItem();
		item1.setFurigana("furi");
		item1.setText("text");
		SubtitleItem.Inner inner11 = new SubtitleItem.Inner();
		SubtitleItem.Inner inner12 = new SubtitleItem.Inner();
		SubtitleItem.Inner inner13 = new SubtitleItem.Inner();
		item1.setElements(Arrays.asList(inner11, inner12, inner13));

		SubtitleItem item2 = new SubtitleItem();
		item2.setFurigana("fuari");
		item2.setText("tsfext");
		SubtitleItem.Inner inner21 = new SubtitleItem.Inner();
		SubtitleItem.Inner inner22 = new SubtitleItem.Inner();
		item1.setElements(Arrays.asList(inner21, inner22));

		SubtitleItem item3 = new SubtitleItem();
		item3.setText("tteee");
		line.setItems(Arrays.asList(item1, item2, item3));
		return line;
	}
}
