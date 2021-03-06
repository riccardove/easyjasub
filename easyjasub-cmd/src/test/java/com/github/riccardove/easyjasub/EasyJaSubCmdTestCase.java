package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-cmd
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

import junit.framework.TestCase;

public abstract class EasyJaSubCmdTestCase extends TestCase {

	private static final File samplesFile = new File("samples");

	protected File getSampleFile(String name) {
		File file = new File(samplesFile, name);
		assertTrue("Could not find file " + file.getAbsolutePath() + " from "
				+ SystemProperty.getUserDir(), file.exists());
		return file;
	}

	protected File getSamplesDir() {
		return samplesFile;
	}
}
