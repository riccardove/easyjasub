package com.github.riccardove.easyjasub;

import static org.rendersnake.HtmlAttributesFactory.charset;
import static org.rendersnake.HtmlAttributesFactory.lang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

class SubtitleListHtmlFilesWriter {
	private final File htmlFolder;
	private final String cssFile;

	public SubtitleListHtmlFilesWriter(File htmlFolder, String cssFileUrl){
		this.htmlFolder = htmlFolder;
		this.cssFile = cssFileUrl;
	}

	public void writeHtmls(SubtitleList s) throws IOException{
		ArrayList<FileWriter> writers = new ArrayList<FileWriter>(s.size());
		for (SubtitleLine l : s) {
			String htmlStr = toHtml(l, cssFile);
			
			File file = new File(htmlFolder, l.getHtmlFile());
			writers.add(toFile(htmlStr, file));
		}
		for (FileWriter writer : writers) {
			writer.close();
		}
	}
	
	private static String toHtml(Renderable s, String cssFileRef) throws IOException 
	{
		HtmlCanvas html = new HtmlCanvas();
		html.write("<!DOCTYPE html>", false)
		.html(lang("ja"))
		.head()
		.meta(charset("utf-8"))
		.write("<link href=\"" + cssFileRef + "\" rel=\"stylesheet\" type=\"text/css\" />", false)
		._head().body();
		s.renderOn(html);
		html._body()._html();

		return html.toHtml();
	}
	
	private static FileWriter toFile(String text, File fileName) throws IOException
	{
		FileWriter fw = new FileWriter(fileName);
		fw.write(text);
		return fw;
	}

}
