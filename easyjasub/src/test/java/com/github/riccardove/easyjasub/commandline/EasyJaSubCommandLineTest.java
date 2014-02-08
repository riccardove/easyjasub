package com.github.riccardove.easyjasub.commandline;

import static org.junit.Assert.*;

import java.io.PrintWriter;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Test;

public class EasyJaSubCommandLineTest {

	private static final PrintWriter NULL_WRITER =
			new PrintWriter(NullOutputStream.NULL_OUTPUT_STREAM);
	
	@Test
	public void printHelp() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.printHelp(NULL_WRITER);
	}

	@Test
	public void parseHelp() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.parse(new String[] { "-h"});
		assertTrue(cm.isHelp());
		assertNull(cm.getMessage());
	}

	@Test
	public void getVideoFileName() {
		EasyJaSubCommandLine cm = new EasyJaSubCommandLine();
		cm.parse(new String[] { "-v", "VFFF"});
		assertNull("VFFF", cm.getVideoFileName());
	}

}
