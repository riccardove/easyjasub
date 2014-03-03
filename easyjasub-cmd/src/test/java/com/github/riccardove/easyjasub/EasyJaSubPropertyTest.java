package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.junit.Test;

public class EasyJaSubPropertyTest extends EasyJaSubTestCase {

	@Test
	public void testProperties() {
		assertNotNull(EasyJaSubProperty.getName());
		assertNotNull(EasyJaSubProperty.getVersion());
		assertNotNull(EasyJaSubProperty.getDate());
		assertNotNull(EasyJaSubProperty.getIssuesManagementUrl());
		
		assertEquals("easyjasub-cmd", EasyJaSubProperty.getName());
		assertFalse("unknownversion".equals(EasyJaSubProperty.getVersion()));
		assertFalse("unknowndate".equals(EasyJaSubProperty.getDate()));
		assertEquals("https://github.com/riccardove/easyjasub/issues", EasyJaSubProperty.getIssuesManagementUrl());
	}
}
