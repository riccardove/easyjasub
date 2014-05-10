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
import java.io.InputStream;

import junit.framework.TestCase;

import com.github.riccardove.easyjasub.jmdict.JMDictParserTest;

public abstract class EasyJaSubTestCase extends TestCase {

	private static final File samplesFile = new File("samples");
	private static final File samplesHtmlFile = new File(samplesFile, "html");
	
	private static final boolean IS_ECLIPSE;
	static {
		boolean isEclipse = false;
		try {
			StackTraceElement[] stackTrace = Thread.currentThread()
					.getStackTrace();
			String runner = stackTrace[stackTrace.length - 1].toString();
			isEclipse = runner.startsWith("org.eclipse");
			if (!isEclipse) {
				System.out.println("Running tests from: " + runner);
			}
		} catch (Throwable e) {
			isEclipse = false;
		}
		IS_ECLIPSE = isEclipse;
	}

	protected static File getSampleFile(String name) {
		File file = new File(samplesFile, name);
		assertTrue("Could not find file " + file.getAbsolutePath() + " from " + SystemProperty.getUserDir(), file.exists());
		return file;
	}

	protected static File getSampleHtmlOutputDirectory() {
		return samplesHtmlFile;
	}

	protected static File getSampleHtmlOutputDirectory(String directory,
			String name) {
		return new File(samplesHtmlFile, directory + File.separator + name);
	}

	protected static String getMeCabExePath() {
		return "C:/Program Files (x86)/MeCab/bin/mecab.exe";
	}

	protected static String getWkhtmltoimagePath() {
		return "C:/Program Files (x86)/wkhtmltopdf/wkhtmltoimage.exe";
	}

	protected static boolean isEclipse() {
		return IS_ECLIPSE;
	}

	protected static void println(String text) {
		if (IS_ECLIPSE) {
			System.out.println(text);
		}
	}

	protected File getJMDictFile() {
		File jmDictFile = new File(
				EasyJaSubHomeDir.getDefaultHomeDir("easyjasub"),
				"JMDict_e.xml");
		assertTrue(
				"Can not find JMDict file in " + jmDictFile.getAbsolutePath(),
				jmDictFile.exists() || !isEclipse());
		return jmDictFile;
	}

	protected InputStream getJMDictTestResource() {
		return JMDictParserTest.class.getResourceAsStream("JMdict_sample");
	}
}

