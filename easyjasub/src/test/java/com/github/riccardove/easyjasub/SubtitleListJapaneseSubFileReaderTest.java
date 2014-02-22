package com.github.riccardove.easyjasub;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SubtitleListJapaneseSubFileReaderTest {

	@Test
	public void test() throws Exception {
		//System.out.println(SystemProperty.getUserDir());
		File file = new File("samples\\sample1.ja.ass");
		assertTrue(file.exists());
		SubtitleList s = new SubtitleList("test");
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, file, SubtitleFileType.ASS, new Observer());
		assertTrue(s.size() > 0);
		for (SubtitleLine line : s) {
			assertTrue(line.isJa());
			assertFalse(line.isTranslation());
			//System.out.println(line.getIndex() + " (" + line.getStartTime() + "->" + line.getEndTime() + ") " + line.getJapaneseText());
		}
		//System.out.println(s.size());
	}

	private class Observer extends FakeEasyJaSubObserver {
		
	}
}
