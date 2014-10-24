package com.github.riccardove.easyjasub.html2image;

import gui.ava.html.Html2Image;

import java.io.File;

public class Html2ImagePngRenderer {
	public void fromHtml(String html, int width, int height, File pngFile) {
		saveImage(Html2Image.fromHtml(html), width, height, pngFile);
	}

	public void fromFile(File htmlFile, int width, int height, File pngFile) {
		saveImage(Html2Image.fromFile(htmlFile), width, height, pngFile);
	}

	private void saveImage(Html2Image imageGenerator, int width, int height,
			File pngFile) {
		imageGenerator.getImageRenderer().setWidth(width).setHeight(height)
				.setImageType("png").saveImage(pngFile);
	}
}
