package com.github.riccardove.easyjasub;

import org.junit.Ignore;

@Ignore
public class SystemPropertyTest extends EasyJaSubTestCase {

	public void test() {
		assertEquals(EasyJaSubCharset.CHARSETSTR, SystemProperty.getEncoding());
	}
}
