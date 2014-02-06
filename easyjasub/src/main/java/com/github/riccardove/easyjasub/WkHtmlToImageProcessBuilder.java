package com.github.riccardove.easyjasub;

import java.io.IOException;

class WkHtmlToImageProcessBuilder {
	public WkHtmlToImageProcessBuilder() {
		wkhtmltoimageexe = "C:\\Program Files (x86)\\wkhtmltopdf\\wkhtmltoimage.exe";
	}
	
	private final String wkhtmltoimageexe;
	
	public Process start(String htmlFile, String pngFile, int width) throws IOException {
		return new ProcessBuilder(wkhtmltoimageexe,
				"--width", Integer.toString(width), //"--disable-smart-width",
				"--transparent",
				htmlFile, pngFile).start();
	}
}
