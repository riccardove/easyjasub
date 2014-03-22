package com.github.riccardove.easyjasub;

import junit.framework.TestCase;

import org.junit.Test;

public class EasyJaSubCmdLicenseTest extends TestCase {

	@Test
	public void testLicense() {
		assertNotNull(EasyJaSubCmdLicense.getLicense());
		assertEquals("easyjasub-cmd ", EasyJaSubCmdLicense.getLicense()
				.substring(0, 14));
	}
}
