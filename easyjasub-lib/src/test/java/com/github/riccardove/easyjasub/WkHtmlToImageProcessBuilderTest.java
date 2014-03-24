package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import org.junit.Ignore;

@Ignore
public class WkHtmlToImageProcessBuilderTest extends EasyJaSubTestCase {

	public void test() {
		for (File dir : getSampleHtmlOutputDirectory().listFiles(
				new DirFilter())) {
			for (File di : dir.listFiles(new DirFilter())) {
				for (File htmlFile : di.listFiles(new HtmlFilter())) {
					convert(htmlFile);
				}
			}
		}
	}

	private void convert(File htmlFile) {
		// TODO Auto-generated method stub

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
