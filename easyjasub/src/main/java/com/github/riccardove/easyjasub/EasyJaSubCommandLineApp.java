package com.github.riccardove.easyjasub;

import java.io.*;

import com.github.riccardove.easyjasub.commandline.EasyJaSubCommandLine;

public class EasyJaSubCommandLineApp {
	public EasyJaSubCommandLineApp() {
		commandLine = new EasyJaSubCommandLine();
	}

	EasyJaSubCommandLine commandLine;
	
	public int run(String[] args, PrintWriter outputStream, PrintWriter errorStream) {
		outputStream.flush();
		if (!commandLine.parse(args)) {
			printVersion(errorStream);
			errorStream.println("Command invocation error:");
			errorStream.println(commandLine.getMessage());
			errorStream.flush();
			return -1;
		}
		if (commandLine.isHelp()) {
			printVersion(outputStream);
			commandLine.printHelp(outputStream);
			outputStream.print("Issues management: ");
			outputStream.println(EasyJaSubProperty.getIssuesManagementUrl());
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
			printVersion(errorStream);
			errorStream.println("Command error:");
			errorStream.println(ex.getMessage());
			errorStream.flush();
			return -2;
		}
		catch (Exception ex) {
			printVersion(errorStream);
			errorStream.println("Unexpected command error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.print("Issues management: ");
			errorStream.println(EasyJaSubProperty.getIssuesManagementUrl());
			errorStream.flush();
			return -100;
		}
		try {
			return new EasyJaSub().run(input, new EasyJaSubConsole(outputStream, errorStream));
		}
		catch (EasyJaSubException ex) {
			printVersion(errorStream);
			errorStream.println("Execution error:");
			errorStream.println(ex.getMessage());
			errorStream.flush();
			return -3;
		}
		catch (Exception ex) {
			printVersion(errorStream);
			errorStream.println("Unexpected error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.print("Issues management: ");
			errorStream.println(EasyJaSubProperty.getIssuesManagementUrl());
			errorStream.flush();
			return -100;
		}
	}

	private void printVersion(PrintWriter outputStream) {
		outputStream.print(EasyJaSubProperty.getName());
		outputStream.print(" ");
		outputStream.print(EasyJaSubProperty.getVersion());
		outputStream.print(" ");
		outputStream.print(EasyJaSubProperty.getUrl());
		outputStream.print(" built on ");
		outputStream.print(EasyJaSubProperty.getDate());
		outputStream.println();
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
