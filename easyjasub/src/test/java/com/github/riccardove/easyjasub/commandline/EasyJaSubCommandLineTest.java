package com.github.riccardove.easyjasub.commandline;

import java.io.PrintWriter;

import junit.framework.TestCase;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Test;

public class EasyJaSubCommandLineTest extends TestCase {

	private static final PrintWriter NULL_WRITER =
			new PrintWriter(NullOutputStream.NULL_OUTPUT_STREAM);
	
	@Test
	public void testPrintHelp() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.printHelp(NULL_WRITER, "javajar");
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
