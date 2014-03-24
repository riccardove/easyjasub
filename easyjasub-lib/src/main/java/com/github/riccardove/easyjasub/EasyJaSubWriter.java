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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Simple helper to write text files
 */
public class EasyJaSubWriter {

	private final OutputStreamWriter writer;
	public static final String Newline = SystemProperty.getLineSeparator();

	public EasyJaSubWriter(File file) throws IOException {
		this(new FileOutputStream(file));
	}

	public EasyJaSubWriter(OutputStream outputStream) throws IOException {
		this.writer = new OutputStreamWriter(outputStream,
				EasyJaSubCharset.CHARSET);
	}

	public void print(String text) throws IOException {
		writer.append(text);
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

	public void flush() throws IOException {
		writer.flush();
	}
}
