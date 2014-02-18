package com.github.riccardove.easyjasub;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SubtitleListTranslatedSubFileReaderTest {

	@Test
	public void test() throws Exception {
		SubtitleList s = new SubtitleList("test");

		System.out.println(SystemProperty.getUserDir());
		File fileJa = new File("samples\\sample1.ja.ass");
		assertTrue(fileJa.exists());
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, fileJa, SubtitleFileType.ASS, new Observer());

		File file = new File("samples\\sample1.en.srt");
		assertTrue(file.exists());
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, file, SubtitleFileType.SRT, new Observer());

	}

	private class Observer extends FakeEasyJaSubObserver {
		
	}
}
