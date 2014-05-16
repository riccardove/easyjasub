package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;

class SubtitleListToHtmlFileWriter {

	public void writeFile(EasyJaSubInput command, SubtitleList list,
			EasyJaSubCssFile cssFile, File file, File htmlDirectory)
			throws IOException {

		String cssFileUrl = cssFile != null ? cssFile
				.toRelativeURIStr(htmlDirectory) : "default.css";

		String htmlStr = new SubtitleLineToHtml(command.isSingleLine(),
				command.showFurigana(), command.showRomaji(),
				command.showDictionary(), command.showKanji(),
				command.showTranslation()).toHtml(list, cssFileUrl);

		// TODO avoid storing the full html in memory
		EasyJaSubWriter writer = new EasyJaSubWriter(file);
		writer.print(htmlStr);
		writer.close();
	}

}
