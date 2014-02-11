package com.github.riccardove.easyjasub;

import java.io.*;

class SubtitleListJapaneseTextFileWriter {
	public void write(SubtitleList s, File file) throws IOException {
		PrintWriter stream = new PrintWriter(new FileOutputStream(file));
		for (SubtitleLine line : s) {
			stream.println(line.getJapaneseText());
		}
		stream.close();
	}
}
