package com.github.riccardove.easyjasub;

import java.io.*;

import com.github.riccardove.easyjasub.commandline.EasyJaSubCommandLine;

public class EasyJaSubCommandLineApp {
	public EasyJaSubCommandLineApp() {
		commandLine = new EasyJaSubCommandLine();
	}

	EasyJaSubCommandLine commandLine;
	
	public int run(String[] args, PrintWriter outputStream, PrintWriter errorStream) {
		if (!commandLine.parse(args)) {
			errorStream.println("Command invocation error:");
			errorStream.println(commandLine.getMessage());
			return -1;
		}
		if (commandLine.isHelp()) {
			commandLine.printHelp(outputStream);
			return 0;
		}
		EasyJaSubInputFromCommand input = null;
		try {
			new EasyJaSubInputFromCommand(commandLine);
		}
		catch (Exception ex) {
			errorStream.println("Command error:");
			errorStream.println(ex.getMessage());
			return -2;
		}
		try {
			return new EasyJaSub().run(input, new EasyJaSubConsole(outputStream, errorStream));
		}
		catch (EasyJaSubException ex) {
			errorStream.println("Execution error:");
			errorStream.println(ex.getMessage());
			return -3;
		}
		catch (Exception ex) {
			errorStream.println("Unexpected error:");
			errorStream.println(ex.getMessage());
			errorStream.println("This error may be a problem in the program, please report it.");
			return -100;
		}
	}
}
