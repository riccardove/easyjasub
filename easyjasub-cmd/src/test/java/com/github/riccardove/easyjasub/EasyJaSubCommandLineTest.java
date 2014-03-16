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


import java.io.PrintWriter;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Test;

public class EasyJaSubCommandLineTest extends EasyJaSubCmdTestCase {

	private static final PrintWriter NULL_WRITER =
			new PrintWriter(NullOutputStream.NULL_OUTPUT_STREAM);
	
	@Test
	public void testPrintHelp() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.printHelp(NULL_WRITER, "javajar", null, null);
	}

	@Test
	public void testParseHelp() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.parse(new String[] { "-h"});
		assertTrue(cm.isHelp());
		assertNull(cm.getMessage());
	}

	@Test
	public void testGetVideoFileName() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.parse(new String[] { "-v", "VFFF"});
		assertNull("VFFF", cm.getVideoFileName());
	}

}
