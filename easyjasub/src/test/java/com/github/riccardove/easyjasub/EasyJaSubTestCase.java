package com.github.riccardove.easyjasub;

import java.io.File;

import junit.framework.TestCase;

public abstract class EasyJaSubTestCase extends TestCase {

	private static final File samplesFile = new File("samples");
	
	protected File getSampleFile(String name) {
		File file = new File(samplesFile, name);
		assertTrue("Could not find file " + file.getAbsolutePath() + " from " + SystemProperty.getUserDir(), file.exists());
		return file;
	}

}
