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
			errorStream.flush();
			return -1;
		}
		if (commandLine.isHelp()) {
			commandLine.printHelp(outputStream);
			outputStream.flush();
			return 0;
		}
		EasyJaSubInputFromCommand input = null;
		try {
			input = new EasyJaSubInputFromCommand(commandLine);
			outputStream.println("Processing " + input.getDefaultFileNamePrefix());
			printFile(outputStream, "Video file: ", input.getVideoFile());
			printFile(outputStream, "Japanese subtitles file: ", input.getJapaneseSubFile());
			printFile(outputStream, "Translated subtitles file: ", input.getTranslatedSubFile());
			printFile(outputStream, "nihongo.j/talk.com file: ", input.getNihongoJtalkHtmlFile());
			printFile(outputStream, "CSS file: ", input.getCssFile());
			printFile(outputStream, "HTML intermediate directory: ", input.getOutputHtmlDirectory());
			printFile(outputStream, "Japanese text file: ", input.getOutputJapaneseTextFile());
			printFile(outputStream, "BDN XML file: ", input.getBdnXmlFile());
			printFile(outputStream, "IDX file: ", input.getOutputIdxFile());
			outputStream.flush();
		}
		catch (EasyJaSubException ex) {
			errorStream.println("Command error:");
			errorStream.println(ex.getMessage());
			errorStream.flush();
			return -2;
		}
		catch (Exception ex) {
			errorStream.println("Unexpected command error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.flush();
			return -100;
		}
		try {
			return new EasyJaSub().run(input, new EasyJaSubConsole(outputStream, errorStream));
		}
		catch (EasyJaSubException ex) {
			errorStream.println("Execution error:");
			errorStream.println(ex.getMessage());
			errorStream.flush();
			return -3;
		}
		catch (Exception ex) {
			errorStream.println("Unexpected error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.flush();
			return -100;
		}
	}
	
	private static void printFile(PrintWriter outputStream, String message, File file)
			throws IOException{
		if (file != null) {
			outputStream.print(message);
			outputStream.print(file.getCanonicalPath());
			if (!file.exists()) {
				outputStream.print('*');
			}
			outputStream.println();
		}
	}
}
