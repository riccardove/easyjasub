package com.github.riccardove.easyjasub;

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
