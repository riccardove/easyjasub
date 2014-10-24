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

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.cssbox.CssBoxPngRenderer;

class SubtitleListPngFilesJavaWriter {

	private final EasyJaSubObserver observer;
	private final int width;

	public SubtitleListPngFilesJavaWriter(int width, EasyJaSubObserver observer) {
		this.width = width;
		this.observer = observer;
	}

	private static final int MaxHeight = 1000;

	public void writeImages(PictureSubtitleList s) throws IOException,
			EasyJaSubException {
		for (PictureSubtitleLine l : s) {
			File file = l.getHtmlFile();
			File pngFile = l.getPngFile();
			if (pngFile.exists()) {
				observer.onWriteImageSkipped(pngFile, file);
			} else {
				observer.onWriteImage(pngFile, file);
				/*
				 * if (l.getHtml() != null) { new
				 * Html2ImagePngRenderer().fromHtml(l.getHtml(), width,
				 * MaxHeight, pngFile); } else
				 */
				if (file != null && file.exists()) {
					try {
						new CssBoxPngRenderer().fromFile(file, width,
								MaxHeight, pngFile);
					} catch (SAXException ex) {
						throw new IOException("Error parsing HTML file", ex);
					}
				} else {
					observer.onWriteImageError(pngFile, file);
				}
				// TODO store the image size
			}
		}
	}

}
