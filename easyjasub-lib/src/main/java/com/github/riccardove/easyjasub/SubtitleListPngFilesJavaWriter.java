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

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

class SubtitleListPngFilesJavaWriter {

	private final EasyJaSubObserver observer;

	public SubtitleListPngFilesJavaWriter(int width, EasyJaSubObserver observer) {
		this.observer = observer;
		imageGenerator = new HtmlImageGenerator();
		imageGenerator.setSize(new Dimension(width, MaxHeight));
	}

	private static final int MaxHeight = 1000;
	private final HtmlImageGenerator imageGenerator;

	public void writeImages(PictureSubtitleList s) throws IOException,
			InterruptedException,
			EasyJaSubException {
		for (PictureSubtitleLine l : s) {
			File file = l.getHtmlFile();
			File pngFile = l.getPngFile();
			if (pngFile.exists()) {
				observer.onWriteImageSkipped(pngFile, file);
			} else {
				observer.onWriteImage(pngFile, file);

				if (l.getHtml() != null) {
					imageGenerator.loadHtml(l.getHtml());
				} else if (file != null && file.exists()) {
					imageGenerator.loadUrl(file.toURI().toURL());
				} else {
					observer.onWriteImageError(pngFile, file);
				}
				imageGenerator.saveAsImage(file.getAbsolutePath());
				// TODO store the image size
			}
		}
	}

}
