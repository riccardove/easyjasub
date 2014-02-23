package com.github.riccardove.easyjasub;

import org.junit.Test;

import junit.framework.TestCase;

public class EasyJaSubPropertyTest extends TestCase {

	@Test
	public void testProperties() {
		assertNotNull(EasyJaSubProperty.getName());
		assertNotNull(EasyJaSubProperty.getVersion());
		assertNotNull(EasyJaSubProperty.getDate());
		assertNotNull(EasyJaSubProperty.getIssuesManagementUrl());
		
		assertEquals("easyjasub", EasyJaSubProperty.getName());
		assertFalse("unknownversion".equals(EasyJaSubProperty.getVersion()));
		assertFalse("unknowndate".equals(EasyJaSubProperty.getDate()));
		assertEquals("https://github.com/riccardove/easyjasub/issues", EasyJaSubProperty.getIssuesManagementUrl());
	}
}
