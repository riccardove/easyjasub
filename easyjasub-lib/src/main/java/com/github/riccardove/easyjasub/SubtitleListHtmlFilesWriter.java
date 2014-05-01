package com.github.riccardove.easyjasub;

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

class SubtitleListHtmlFilesWriter {
	private final EasyJaSubObserver observer;

	public SubtitleListHtmlFilesWriter(EasyJaSubObserver observer) {
		this.observer = observer;
	}

	public void writeHtmls(PictureSubtitleList s, EasyJaSubInput command)
			throws IOException {

		ArrayList<EasyJaSubWriter> writers = new ArrayList<EasyJaSubWriter>(
				s.size());
		for (PictureSubtitleLine l : s) {
			File file = l.getHtmlFile();
			if (!file.exists()) {
				observer.onWriteHtmlFile(file);

				writers.add(toFile(l.getHtml(), file));
			} else {
				observer.onWriteHtmlFileSkipped(file);
			}
			if (writers.size() > 30) {
				closeWriters(writers);
			}
		}
		closeWriters(writers);
	}

	private void closeWriters(ArrayList<EasyJaSubWriter> writers)
			throws IOException {
		for (EasyJaSubWriter writer : writers) {
			writer.close();
		}
		writers.clear();
	}

	private static EasyJaSubWriter toFile(String text, File file)
			throws IOException {
		EasyJaSubWriter fw = new EasyJaSubWriter(file);
		fw.println(text);
		return fw;
	}
}
