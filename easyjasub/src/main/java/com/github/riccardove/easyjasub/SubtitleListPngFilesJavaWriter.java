package com.github.riccardove.easyjasub;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

class SubtitleListPngFilesJavaWriter {
	
	private final EasyJaSubObserver observer;

	public SubtitleListPngFilesJavaWriter(int width,
			EasyJaSubObserver observer) {
		this.observer = observer;
		imageGenerator = new HtmlImageGenerator();
		imageGenerator.setSize(new Dimension(width, MaxHeight));
	}

	private static final int MaxHeight = 1000;
	private final HtmlImageGenerator imageGenerator;

	public void writeImages(SubtitleList s, File htmlFolder, File pngFolder) throws IOException, InterruptedException 
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
	}

}
