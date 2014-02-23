package com.github.riccardove.easyjasub;

import org.junit.Test;

import junit.framework.TestCase;

public class EasyJaSubProgramLocationTest extends TestCase {

	@Test
	public void testLocationStr() {
		assertNotNull(EasyJaSubProgramLocation.getLocationStr());
		assertFalse("unknown".equals(EasyJaSubProgramLocation.getLocationStr()));
	}

	@Test
	public void testName() {
		assertNotNull(EasyJaSubProgramLocation.getName());
	}
}
