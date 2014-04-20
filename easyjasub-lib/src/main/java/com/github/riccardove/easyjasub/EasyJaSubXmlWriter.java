package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;

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

	public void tag(String name, String content)
			throws IOException {
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
