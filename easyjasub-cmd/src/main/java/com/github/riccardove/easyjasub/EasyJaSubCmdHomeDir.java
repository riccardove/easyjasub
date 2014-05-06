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

class EasyJaSubCmdHomeDir {

	private File home;

	public void init(File home) throws EasyJaSubException {
		if (home == null) {
			home = getDefaultHomeDir();
		}
		this.home = home;
		if (!home.exists()) {
			if (!home.mkdirs()) {
				throw new EasyJaSubException("Could not create home dir: "
						+ home.getAbsolutePath());
			}
		}
		if (!home.isDirectory()) {
			throw new EasyJaSubException("Home dir is not a valid directory: "
					+ home.getAbsolutePath());
		}
	}

	private File getDefaultHomeDir() {
		return EasyJaSubHomeDir.getDefaultHomeDir("easyjasub");
	}

	private File jmDictFile;

	public File getJMDictFile() {
		if (jmDictFile == null) {
			jmDictFile = new File(home, "JMdict_e.xml");
			if (!jmDictFile.exists()) {
				jmDictFile = new File(home, "JMdict.xml");
			}
		}
		return jmDictFile;
	}

	private File dictionaryFile;

	public File getDictionaryFile() {
		if (dictionaryFile == null) {
			dictionaryFile = new File(home, "dictionarySerialized.bin");
		}
		return dictionaryFile;
	}
}
