package com.github.riccardove.easyjasub;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.File;
import java.io.IOException;

class SubtitleListPngFilesJavaWriter {
	
	public SubtitleListPngFilesJavaWriter() {
		imageGenerator = new HtmlImageGenerator();
	}

	private final HtmlImageGenerator imageGenerator;

	public int writeImages(SubtitleList s, File htmlFolder, File pngFolder, int width,
			EasyJaSubObserver observer) throws IOException, InterruptedException 
	{
		for (SubtitleLine l : s) {
			
			File file = new File(htmlFolder, l.getHtmlFile());
			File pngFile = new File(pngFolder, l.getPngFile());
			observer.onWriteImage(pngFile, file);
			
			imageGenerator.loadUrl(file.toURI().toURL());
			// TODO avoid rereading the file if written in this session, use imageGenerator.loadHtml();
			imageGenerator.saveAsImage(file.getAbsolutePath());
			// TODO store the image size
		}
		return 0;
	}

}
