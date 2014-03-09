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


import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class MeCabRunnerTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		MeCabRunner runner = new MeCabRunner(getMeCabExePath(), 10, null);
		runner.append("魔法は　普通に売り買いされ 人々の生活に根づいていた。");
		runner.append(System.getProperty("line.separator"));
		runner.append("依頼に応じて仕事をする。");
		runner.append(System.getProperty("line.separator"));

		runner.close();

		assertNotNull(runner.getOutputLines());
		for (String line : runner.getOutputLines()) {
			assertNotNull(line);
		}
	}
}
