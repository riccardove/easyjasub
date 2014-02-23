package com.github.riccardove.easyjasub;

import junit.framework.TestCase;

public class FontListTest extends TestCase {

	public void testListAvailable() {
		FontList list = new FontList();
		assertTrue(list.iterator().hasNext());
//		for (String name : list) {
//			System.out.println(name);
//		}
	}
}
