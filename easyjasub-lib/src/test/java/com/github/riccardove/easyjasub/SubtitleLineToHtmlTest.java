package com.github.riccardove.easyjasub;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

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

public class SubtitleLineToHtmlTest extends EasyJaSubTestCase {

	private static final String Css = "default.css";

	private static SubtitleList sample1List;

	static {
		sample1List = readSampleSubtitleList("sample1.xml");
	}

	private static SubtitleList readSampleSubtitleList(String fileName) {
		try {
			SubtitleList list = new SubtitleList();
			new SubtitleListXmlFileReader(list).read(getSampleFile(fileName));
			int index = 0;
			for (SubtitleLine line : list) {
				line.setIndex(++index);
			}
			return list;
		} catch (Exception ex) {
			return null;
		}
	}

	private static String toHtml(SubtitleLine line, boolean hasWkhtml,
			boolean hasFurigana, boolean hasRomaji, boolean hasDictionary,
			boolean hasKanji, boolean showTranslation) throws Exception {
		return new SubtitleLineToHtml(hasWkhtml, hasFurigana, hasRomaji,
				hasDictionary, hasKanji, showTranslation).toHtml(line, Css);
	}

	public void testDefault() throws Exception {
		runTestOnSample("default", "sample1", sample1List, true, true, false,
				false, true, true);
	}

	public void testTableFormatDefault() throws Exception {
		runTestOnSample("defaulttable", "sample1", sample1List, false, true,
				false, false, true, true);
	}

	public void testTableFormatNoFurigana() throws Exception {
		runTestOnSample("nofuriganatable", "sample1", sample1List, false, false,
				false, false, true, true);
	}

	public void testTableFormatRomaji() throws Exception {
		runTestOnSample("romajitable", "sample1", sample1List, false, false,
				true, false, true, true);
	}

	public void testAddRomaji() throws Exception {
		runTestOnSample("addromaji", "sample1", sample1List, false, true, true,
				false, true, true);
	}

	public void testRomaji() throws Exception {
		runTestOnSample("romaji", "sample1", sample1List, true, false, true,
				false, true, true);
	}

	public void testOnlyRomaji() throws Exception {
		runTestOnSample("onlyromaji", "sample1", sample1List, true, false,
				true, false, false, true);
	}

	public void testOnlyFurigana() throws Exception {
		runTestOnSample("onlyfurigana", "sample1", sample1List, true, true,
				false,
				false, false, true);
	}

	public void testFuriganaAndRomaji() throws Exception {
		runTestOnSample("furiganaromaji", "sample1", sample1List, true, true,
				true, false, false, true);
	}

	private void runTestOnSample(String name, String sample, SubtitleList list,
			boolean hasWkhtml, boolean hasFurigana, boolean hasRomaji,
			boolean hasDictionary, boolean hasKanji, boolean showTranslation) throws Exception {
		File directory = getSampleHtmlOutputDirectory(name, sample);
		for (SubtitleLine line : list) {
			String html = toHtml(line, hasWkhtml, hasFurigana, hasRomaji,
					hasDictionary, hasKanji, showTranslation);

			String fileName = "line" + String.format("%04d", line.getIndex())
					+ ".html";
			File file = new File(directory, fileName);
			if (!file.exists()) {
				// writes the sample file if it does not exist
				if (!directory.exists()) {
					directory.mkdirs();
				}
				new EasyJaSubWriter(file).print(html)
						.close();
			} else {
				EasyJaSubReader reader = new EasyJaSubReader(file);
				String lineStr = null;
				String htmlLineStr = null;
				int index = 0;
				BufferedReader htmlReader = new BufferedReader(
						new StringReader(html));
				do {
					++index;
					lineStr = reader.readLine();
					htmlLineStr = htmlReader.readLine();
					assertEquals("Error at line " + index, lineStr, htmlLineStr);
				} while (lineStr != null);
				reader.close();
				htmlReader.close();
			}
		}
	}
}
