package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class SubtitleListCssFileWriter {
	
	public SubtitleListCssFileWriter(File file) throws IOException {
		writer = new FileWriter(file);
	}
	
	private static final String LineSeparator = SystemProperty.getLineSeparator();
	private final FileWriter writer;
	
	private void w(String line) throws IOException {
		writer.write(line);
		w();
	}
	
	private void w() throws IOException {
		writer.write(LineSeparator);
	}
	
	public void write() throws IOException {
		// TODO: allow specifying sizes
		w("body {");
		w("	text-shadow: -2px 0 black, 0 2px black, 2px 0 black, 0 -2px black;");
		w("	color: white;");
		w("	text-align: center;");
		w("}");
		w();
		w("p {");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		w("	font-family: arial;");
		w("	font-size: 14pt;");
		w("}");
		w();
		w("table {");
		w("	letter-spacing: -4px;");
		w("	margin-left: auto;");
		w("	margin-right: auto;");
		w("}");
		w();
		w("tr.top {");
		w("	letter-spacing: -2px;");
		w("	font-family: cinecaption;");
		w("	font-size: 18pt;");
		w("}");
		w();
		w("tr.bottom {");
		w("	font-family: arial;");
		w("	font-size: 12pt;");
		w("	letter-spacing: 3px;");
		w("}");
		w();
		w("tr.center {");
		w("	padding-left: 2px;");
		w("	padding-right: 2px;");
		w("}");
		w();
		w("tr.translation {");
		w("	font-family: arial;");
		w("	font-size: 12pt;");
		w("	text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;");
		w("	letter-spacing: 1px;");
		w("}");
		w();
		w("td {");
		w("	text-align: center;");
		w("}");
		w();
		w("tr {");
		w("	font-family: cinecaption;");
		w("	font-size: 25pt;");
		w("}");
		w();
		w("span.kk {");
		w("	letter-spacing: -6px;");
		w("	font-family: GT2000-01;");
		w("	font-size: 40pt;");
		w("	margin-left: 0;");
		w("	margin-right: 0;");
		w("	padding: 0;");
		w("}");
		w();
		writer.close();
	}
}
