package com.github.riccardove.easyjasub.mecab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class MeCabProcessTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		parse(1, 27);
	}

	public void testWithManyLines() throws Exception {
		parse(200, 5008);
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
			w.append("依頼に応じて仕事をする。");
		}
		w.close();

		assertEquals(0, obj.waitFor());
		inputThread.join();

		assertEquals(count, lines.size());
		assertNull(lines.get(lines.size() - 1));
	}
}
