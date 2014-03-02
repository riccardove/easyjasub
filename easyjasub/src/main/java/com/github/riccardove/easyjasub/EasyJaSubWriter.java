package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

class EasyJaSubWriter {

	private final OutputStreamWriter writer;
	public static final String Newline = SystemProperty.getLineSeparator();

	public EasyJaSubWriter(File file) throws IOException {
		this.writer = new OutputStreamWriter(new FileOutputStream(file),
				EasyJaSubCharset.CHARSET);
	}

	public void println(String text) throws IOException {
		writer.append(text);
		println();
	}

	public void close() throws IOException {
		writer.close();
	}

	public void println() throws IOException {
		writer.append(Newline);
	}
}
