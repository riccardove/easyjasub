package com.github.riccardove.easyjasub.mecab;

import java.io.File;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;
import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;
import com.github.riccardove.easyjasub.SubtitleListJapaneseSubFileReaderTest;

public class InputMeCabTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		File meCabOutputFile = getSampleFile("samplemecabout.txt");
		File subtitleFile = getSampleFile("sample2.ja.ass");

		Observer observer = new Observer();

		SubtitleList subs = SubtitleListJapaneseSubFileReaderTest
				.getSubtitleList(observer, subtitleFile);
		assertEquals(3, subs.size());

		new InputMeCab(observer, null).run(subs, meCabOutputFile);


		for (SubtitleLine line : subs) {
			println(line.toString());
		}

		SubtitleLine line1 = subs.get(0);
		SubtitleLine line2 = subs.get(1);
		SubtitleLine line3 = subs.get(2);

		assertNotNull(line1.getItems());
		assertNotNull(line2.getItems());
		assertNotNull(line3.getItems());

	}

	private class Observer extends FakeEasyJaSubObserver {

	}
}

