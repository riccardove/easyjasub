package com.github.riccardove.easyjasub.mecab;

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
