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

/**
 * Utility class for writing XML files
 */
public class EasyJaSubXmlWriter {

	public EasyJaSubXmlWriter(File file) throws IOException {
		writer = new EasyJaSubWriter(file);
		writeln("<?xml version=\"1.0\" encoding=\""
				+ EasyJaSubCharset.CHARSETSTR + "\"?>");
	}

	private final EasyJaSubWriter writer;

	public void writeln(String line) throws IOException {
		writer.println(line);
	}

	public void writeln() throws IOException {
		writer.println();
	}

	public void write(String line) throws IOException {
		writer.print(line);
	}

	public void close() throws IOException {
		writer.close();
	}

	private static final String IndentStr = "  ";
	private int indent;

	public void groupOpen(String name) throws IOException {
		printIndent();
		writeln("<" + name + ">");
		indent++;
	}

	public void tagOpenInline(String name) throws IOException {
		write("<" + name + ">");
	}

	public void tagWithAttributeOpenInline(String name, String attribute,
			String attributeContent) throws IOException {
		write("<" + name + " " + attribute + "=\"" + attributeContent + "\">");
	}

	public void groupClose(String name) throws IOException {
		--indent;
		printIndent();
		writeln("</" + name + ">");
	}

	public void tagCloseInline(String name) throws IOException {
		write("</" + name + ">");
	}

	public void tag(String name, String content) throws IOException {
		if (content != null) {
			printIndent();
			writeln("<" + name + ">" + content + "</" + name + ">");
		}
	}

	public void tagWithAttribute(String name, String attribute,
			String attributeContent, String content) throws IOException {
		printIndent();
		writeln("<" + name + " " + attribute + "=\"" + attributeContent + "\">"
				+ content + "</" + name + ">");
	}

	public void tagWithAttribute(String name, String attribute1,
			String attributeContent1, String attribute2,
			String attributeContent2, String content) throws IOException {
		printIndent();
		writeln("<" + name + " " + attribute1 + "=\"" + attributeContent1
				+ "\" " + attribute2 + "=\"" + attributeContent2 + "\">"
				+ content + "</" + name + ">");
	}

	public void tagWithAttributeInline(String name, String attribute,
			String attributeContent, String content) throws IOException {
		write("<" + name + " " + attribute + "=\"" + attributeContent + "\">"
				+ content + "</" + name + ">");
	}

	public void tag(String name) throws IOException {
		printIndent();
		writeln("<" + name + " />");
	}

	public void printIndent() throws IOException {
		for (int i = 0; i < indent; ++i) {
			writer.print(IndentStr);
		}
	}

}
