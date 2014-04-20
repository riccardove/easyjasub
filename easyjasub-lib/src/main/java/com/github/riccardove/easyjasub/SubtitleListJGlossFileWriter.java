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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SubtitleListJGlossFileWriter {
	public void write(SubtitleList s, File file) throws IOException,
			FileNotFoundException {
		f = new EasyJaSubXmlWriter(file);

		groupOpen(SubtitleListJGlossElement.jgloss);
		groupOpen(SubtitleListJGlossElement.head);
		tag(SubtitleListJGlossElement.title, s.getTitle());
		tag(SubtitleListJGlossElement.generator, EasyJaSubProperty.getName()
				+ " " + EasyJaSubProperty.getVersion());
		groupClose(SubtitleListJGlossElement.head);

		groupOpen(SubtitleListJGlossElement.body);
		groupOpen(SubtitleListJGlossElement.div);

		for (SubtitleLine l : s) {
			if (l.getItems() != null) {
				f.printIndent();
				f.tagOpenInline(SubtitleListJGlossElement.p.toString());
				for (SubtitleItem item : l.getItems()) {
					String reading = item.getFurigana();
					boolean romajiReading = false;
					if (reading == null) {
						reading = item.getRomaji();
						romajiReading = true;
					}
					if (reading != null) {
						String attribute = item.getDictionary();
						if (attribute == null && !romajiReading) {
							attribute = item.getRomaji();
						}
						if (attribute == null) {
							f.tagOpenInline(SubtitleListJGlossElement.anno
									.toString());
						} else {
							f.tagWithAttributeOpenInline(
									SubtitleListJGlossElement.anno.toString(),
									"tr", attribute);
						}
						rbase(item.getText(), reading);
						f.tagCloseInline(SubtitleListJGlossElement.anno
								.toString());
					} else {
						f.write(item.getText() + " ");
					}
				}
				f.tagCloseInline(SubtitleListJGlossElement.p.toString());
				f.writeln();
			} else {
				tag(SubtitleListJGlossElement.p, l.getSubText());
			}
		}
		groupClose(SubtitleListJGlossElement.div);
		groupClose(SubtitleListJGlossElement.body);
		groupClose(SubtitleListJGlossElement.jgloss);
		f.close();
	}

	private EasyJaSubXmlWriter f;

	private void groupOpen(SubtitleListJGlossElement name) throws IOException {
		f.groupOpen(name.toString());
	}

	private void groupClose(SubtitleListJGlossElement name) throws IOException {
		f.groupClose(name.toString());
	}

	private void tag(SubtitleListJGlossElement name, String content)
			throws IOException {
		f.tag(name.toString(), content);
	}

	private void rbase(String content, String furigana) throws IOException {
		f.tagWithAttributeInline(SubtitleListJGlossElement.rbase.toString(),
				"re",
				furigana, content);
	}

}
