package com.github.riccardove.easyjasub;


public class EasyJaSubLicenseTest extends EasyJaSubTestCase {

	public void testLicense() {
		assertNotNull(EasyJaSubLicense.getLicense());
		assertEquals("easyjasub-lib ",
				EasyJaSubLicense.getLicense().substring(0, 14));
	}
}
