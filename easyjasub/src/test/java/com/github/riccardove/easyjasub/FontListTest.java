package com.github.riccardove.easyjasub;

public class FontListTest extends EasyJaSubTestCase {

	public void testListAvailable() {
		FontList list = new FontList();
		assertTrue(list.iterator().hasNext());
//		for (String name : list) {
//			System.out.println(name);
//		}
	}
}
