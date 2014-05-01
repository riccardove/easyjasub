package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;

class SubtitleListToPictureSubtitleList {

	private final File cssFile;
	private final File htmlDirectory;

	public SubtitleListToPictureSubtitleList(File htmlDirectory, File cssFile) {
		this.htmlDirectory = htmlDirectory;
		this.cssFile = cssFile;
	}

	private static String toRelativeURIStr(File file, File directory) {
		if (directory == null) {
			return file.toURI().toString();
		}
		return directory.toURI().relativize(file.toURI()).toString();
	}

	public PictureSubtitleList writeHtmls(String filePrefix, SubtitleList s,
			EasyJaSubInput command, File htmlFolder, File bdnFolder)
			throws IOException {
		PictureSubtitleList list = new PictureSubtitleList();
		list.setTitle(s.getTitle());

		// TODO construct relative url
		String cssFileUrl = cssFile != null ? toRelativeURIStr(cssFile,
				htmlDirectory) : "default.css";

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
