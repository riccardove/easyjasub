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

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleListToHtmlFileWriter {

	public void writeFile(EasyJaSubInput command, SubtitleList list,
			EasyJaSubCssFile cssFile, File file, File htmlDirectory)
			throws IOException {

		String cssFileUrl = cssFile != null ? cssFile
				.toRelativeURIStr(htmlDirectory) : "default.css";

		SubtitleLineToHtml converter = new SubtitleLineToHtml(
				command.isSingleLine(),
				command.getWkHtmlToImageCommand() != null,
				command.showFurigana(), command.showRomaji(),
				command.showDictionary(), command.showKanji(),
				command.showTranslation());

		String htmlStr = toHtml(converter, list, cssFileUrl);

		// TODO avoid storing the full html in memory
		EasyJaSubWriter writer = new EasyJaSubWriter(file);
		writer.print(htmlStr);
		writer.close();
	}

	private String toHtml(SubtitleLineToHtml converter, SubtitleList list,
			String cssFileRef) throws IOException {
		RendersnakeHtmlCanvas html = converter.createHtmlCanvas(cssFileRef);
		for (SubtitleLine line : list) {
			html.div();
			int s = line.getStartTime() / 1000;
			String time = s / 60 + ":" + s % 60;
			html.writeUnescaped("<h2>" + time + "</h2>");
			converter.appendHtmlBodyContent(line, html);
			html._div();
		}
		html.footer();
		return html.toString();
	}

}
