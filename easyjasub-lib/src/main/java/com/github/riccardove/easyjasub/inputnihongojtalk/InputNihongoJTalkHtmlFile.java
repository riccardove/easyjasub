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
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.EasyJaSubXmlReader;
import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

public class InputNihongoJTalkHtmlFile {

	private static final String TAGSOUP_PARSER = "org.ccil.cowan.tagsoup.Parser";

	public void parse(File file, SubtitleList subs, EasyJaSubObserver observer)
			throws IOException, SAXException {
		NihongoJTalkSubtitleList nlist = new NihongoJTalkSubtitleList();
		InputNihongoJTalkHtmlHandler handler = new InputNihongoJTalkHtmlHandler(
				nlist, observer);

		EasyJaSubXmlReader saxParser = new EasyJaSubXmlReader(TAGSOUP_PARSER,
				handler, handler);
		saxParser.parse(file);

		int index = 0;
		int nIndex = 0;
		int size = subs.size();
		int nsize = nlist.size();
		ArrayList<Integer> subsSkipped = new ArrayList<Integer>();
		ArrayList<Integer> nSkipped = new ArrayList<Integer>();
		while (index < size && nIndex < nsize) {
			SubtitleLine line = subs.get(index);
			if (line.getJapaneseSubKey() == null) {
				index++;
			} else {
				NihongoJTalkSubtitleLine nline = nlist.get(nIndex);
				if (nline.getJapaneseKey() == null) {
					nIndex++;
				} else {
					if (areCompatible(line, nline)) {
						joinLines(nline, line);
						nIndex++;
						index++;
					} else {
						int subsequentNIndex = findInSubsequentNihongoJTalkSubtitleLines(
								line, nIndex, nlist);
						if (subsequentNIndex > 0) {
							joinLines(nlist.get(subsequentNIndex), line);
							index++;
							for (int j = nIndex; j < subsequentNIndex; ++j) {
								nSkipped.add(j);
							}
							nIndex = subsequentNIndex + 1;
						} else {
							int subsequentIndex = findInSubsequentSubtitleLines(
									nline, index, subs);
							if (subsequentIndex > 0) {
								joinLines(nline, subs.get(subsequentIndex));
								nIndex++;
								for (int j = index; j < subsequentIndex; ++j) {
									subsSkipped.add(j);
								}
								index = subsequentIndex + 1;
							}
						}
					}
				}
			}
		}
		if (index < size) {
			for (int j = index; j < size; ++j) {
				subsSkipped.add(j);
			}
		}
		if (nIndex < nsize) {
			for (int j = nIndex; j < nsize; ++j) {
				nSkipped.add(j);
			}
		}
		if (nSkipped.size() > 0 || subsSkipped.size() > 0) {
			observer.onInputNihongoJTalkHtmlLineParseSkipped(nSkipped,
					subsSkipped);
		}
	}

	private int findInSubsequentNihongoJTalkSubtitleLines(SubtitleLine line,
			int startIndex, NihongoJTalkSubtitleList nlist) {
		int size = nlist.size();
		for (int i = startIndex + 1; i < size; ++i) {
			NihongoJTalkSubtitleLine nLine = nlist.get(i);
			if (nLine.getJapaneseKey() != null && areCompatible(line, nLine)) {
				return i;
			}
		}
		return -1;
	}

	private int findInSubsequentSubtitleLines(NihongoJTalkSubtitleLine line,
			int startIndex, SubtitleList nlist) {
		int size = nlist.size();
		for (int i = startIndex + 1; i < size; ++i) {
			SubtitleLine nLine = nlist.get(i);
			if (nLine.getJapaneseSubKey() != null && areCompatible(nLine, line)) {
				return i;
			}
		}
		return -1;
	}

	private boolean areCompatible(SubtitleLine line,
			NihongoJTalkSubtitleLine nline) {
		return nline.getJapaneseKey().equals(line.getJapaneseSubKey());
	}

	private void joinLines(NihongoJTalkSubtitleLine nline, SubtitleLine line) {
		nline.setTranslatedText(line.getTranslation());
		line.setItems(nline.toItems());
	}
}
