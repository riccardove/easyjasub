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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EasyJaSubReader {

	private final BufferedReader reader;

	public EasyJaSubReader(File file) throws IOException {
		this(new FileInputStream(file));
	}

	public EasyJaSubReader(InputStream outputStream) throws IOException {
		this.reader = new BufferedReader(new InputStreamReader(outputStream,
				EasyJaSubCharset.CHARSET));
	}

	public String readLine() throws IOException {
		return reader.readLine();
	}

	public void close() throws IOException {
		reader.close();
	}
}
