package com.github.riccardove.easyjasub;

import java.io.File;

import org.junit.Test;

public class SubtitleListLuceneAnalyzerTest extends EasyJaSubTestCase {

	@Test
	public void testSample4() throws Exception {
		File fileJa = getSampleFile("sample4.ass");

		SubtitleList s = new SubtitleList();

		Observer observer = new Observer();

		new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s,
				fileJa, SubtitleFileType.ASS, observer, null);

		new SubtitleListLuceneAnalyzer(observer).run(s);
	}

	@Test
	public void testSample5() throws Exception {
		File fileJa = getSampleFile("sample5.ass");

		SubtitleList s = new SubtitleList();

		Observer observer = new Observer();

		new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s,
				fileJa, SubtitleFileType.ASS, observer, null);

		new SubtitleListLuceneAnalyzer(observer).run(s);
	}

	@Test
	public void testTicket2Row() throws Exception {
		SubtitleList s = new SubtitleList();

		Observer observer = new Observer();
		s.add().setSubText("その金で　弁護士の最高ステータス\n虎ノ門に事務所を構えたい");

		new SubtitleListLuceneAnalyzer(observer).run(s);
	}

	private class Observer extends EasyJaSubObserverBase {

	}
}
