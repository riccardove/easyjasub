package com.github.riccardove.easyjasub.cssbox;

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

import java.io.File;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class CssBoxPngRendererTest extends EasyJaSubTestCase {

	public void testFromFileSample6() throws Exception {
		File htmlFile = getHtmlFile();
		File pngFile = getPngFile(htmlFile);
		if (!pngFile.exists()) {
			// skip test if generated file is already present
			new CssBoxPngRenderer().fromFile(htmlFile, 1024, 768, pngFile);
			assertTrue(pngFile.exists());
		}
	}

	private File getPngFile(File htmlFile) {
		return new File(htmlFile.getParentFile(), "sample6.png");
	}

	private File getHtmlFile() {
		return getSampleFile("sample6.html");
	}
}
