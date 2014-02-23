package com.github.riccardove.easyjasub.inputnihongojtalk;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

public class InputNihongoJTalkHtmlFileTest extends TestCase {

	@Test
	public void testParse() throws Exception {
		File file = new File("samples\\sample1.htm");
		assertTrue(file.exists());
		SubtitleList s = new SubtitleList("test");
		new InputNihongoJTalkHtmlFile().parse(file, s, new Observer());
		assertTrue(s.size() > 0);
	}

	private class Observer extends FakeEasyJaSubObserver {
		@Override
		public void onInputNihongoJTalkHtmlLine(SubtitleLine line) {
			//System.out.println(line.getIndex() + ": " + line.toString());
		}
	}

}
