package com.github.riccardove.easyjasub.mecab;

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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.junit.Ignore;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

@Ignore
public class MeCabProcessTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		parse(1, 28);
	}

	public void testWithManyLines() throws Exception {
		parse(200, 5401);
	}

	private void parse(int mult, int count) throws IOException,
			InterruptedException {
		final MeCabProcess obj = new MeCabProcess(getMeCabExePath());
		final ArrayList<String> lines = new ArrayList<String>();

		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedReader r = new BufferedReader(
							new InputStreamReader(obj.getInputStream()));

					String line;
					do {
						line = r.readLine();
						lines.add(line);
					} while (line != null);
				} catch (Exception e) {
					lines.clear();
				}
			}
		});
		inputThread.start();

		Writer w = new BufferedWriter(new OutputStreamWriter(
				obj.getOutputStream()));
		for (int i = 0; i < mult; ++i) {
			w.append("魔法は　普通に売り買いされ 人々の生活に根づいていた。");
			w.append(System.getProperty("line.separator"));
			w.append("依頼に応じて仕事をする。");
			w.append(System.getProperty("line.separator"));
		}
		w.close();

		assertEquals(0, obj.waitFor());
		inputThread.join();

		assertEquals(count, lines.size());
		assertNull(lines.get(lines.size() - 1));
	}
}
