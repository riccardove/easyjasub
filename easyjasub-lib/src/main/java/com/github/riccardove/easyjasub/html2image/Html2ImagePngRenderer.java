package com.github.riccardove.easyjasub.html2image;

/*
 * #%L
 * easyjasub-lib
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
