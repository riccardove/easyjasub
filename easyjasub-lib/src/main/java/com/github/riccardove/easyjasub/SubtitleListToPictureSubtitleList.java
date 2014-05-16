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
import java.io.IOException;

class SubtitleListToPictureSubtitleList {

	private final EasyJaSubCssFile cssFile;
	private final File htmlDirectory;

	public SubtitleListToPictureSubtitleList(File htmlDirectory,
			EasyJaSubCssFile cssFile) {
		this.htmlDirectory = htmlDirectory;
		this.cssFile = cssFile;
	}

	public PictureSubtitleList writeHtmls(String filePrefix, SubtitleList s,
			EasyJaSubInput command, File htmlFolder, File bdnFolder)
			throws IOException {
		PictureSubtitleList list = new PictureSubtitleList();
		list.setTitle(s.getTitle());

		String cssFileUrl = cssFile != null ? cssFile
				.toRelativeURIStr(htmlDirectory) : "default.css";

		int index = 0;
		for (SubtitleLine l : s) {
			++index;
			String htmlStr = new SubtitleLineToHtml(command.isSingleLine(),
					command.showFurigana(), command.showRomaji(),
					command.showDictionary(), command.showKanji(),
					command.showTranslation()).toHtml(l, cssFileUrl);
			PictureSubtitleLine pictureLine = list.add();
			pictureLine.setHtml(htmlStr);
			pictureLine.setEndTime(l.getEndTime());
			pictureLine.setStartTime(l.getStartTime());

			String b = filePrefix + "_line" + String.format("%04d", index);
			// TODO: create File class only where used
			if (htmlFolder != null) {
				pictureLine.setHtmlFile(new File(htmlFolder, b + ".html"));
			}
			if (bdnFolder != null) {
				pictureLine.setPngFile(new File(bdnFolder, b + ".png"));
			}
		}
		return list;
	}
}
