package com.github.riccardove.easyjasub;

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
import java.io.FileFilter;
import java.io.FilenameFilter;

public class WkHtmlToImageProcessBuilderTest extends EasyJaSubTestCase {

	public void testConvertAllSampleHtmls() throws Exception {
		if (!isEclipse()) {
			// this is a very slow test, not to be run normally
			return;
		}
		WkHtmlToImageProcessBuilder builder = new WkHtmlToImageProcessBuilder(
				getWkhtmltoimagePath());
		for (File dir : getSampleHtmlOutputDirectory().listFiles(
				new DirFilter())) {
			for (File di : dir.listFiles(new DirFilter())) {
				for (File htmlFile : di.listFiles(new HtmlFilter())) {
					convert(htmlFile, builder);
				}
			}
		}
	}

	private void convert(File htmlFile, WkHtmlToImageProcessBuilder builder)
			throws Exception {
		String htmlFileStr = htmlFile.getAbsolutePath();
		String pngFileStr = htmlFileStr.replace(".html", ".png");
		File pngFile = new File(pngFileStr);
		if (!pngFile.exists()
				|| pngFile.lastModified() < htmlFile.lastModified()) {
			println(pngFileStr);
			Process process = builder.start(htmlFileStr, pngFileStr, 300);
			assertEquals(0, process.waitFor());
		}
	}

	private static class HtmlFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".html");
		}
	}

	private static class DirFilter implements FileFilter {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	}
}
