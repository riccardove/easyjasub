package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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
	private EasyJaSubObserver observer;

	public SubtitleListHtmlFilesWriter(File htmlFolder, String cssFileUrl, EasyJaSubObserver observer){
		this.htmlFolder = htmlFolder;
		this.cssFile = cssFileUrl;
		this.observer = observer;
	}

	public void writeHtmls(SubtitleList s) throws IOException{
		ArrayList<FileWriter> writers = new ArrayList<FileWriter>(s.size());
		for (SubtitleLine l : s) {
			File file = new File(htmlFolder, l.getHtmlFile());
			if (!file.exists()) {
				observer.onWriteHtmlFile(file);
				String htmlStr = toHtml(l, cssFile);
				
				writers.add(toFile(htmlStr, file));
			}
			else {
				observer.onWriteHtmlFileSkipped(file);
			}
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
