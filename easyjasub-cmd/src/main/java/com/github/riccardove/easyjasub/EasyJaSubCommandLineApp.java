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


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.github.riccardove.easyjasub.commandline.EasyJaSubCommandLine;

public class EasyJaSubCommandLineApp {
	public EasyJaSubCommandLineApp() {
		commandLine = new EasyJaSubCommandLine();
	}

	EasyJaSubCommandLine commandLine;
	
	public int run(String[] args, PrintWriter outputStream, PrintWriter errorStream) {
		if (!commandLine.parse(args)) {
			printVersion(errorStream);
			errorStream.println("Command invocation error:");
			errorStream.println(commandLine.getMessage());
			suggestHelp(errorStream);
			errorStream.flush();
			return -1;
		}
		if (commandLine.isHelp()) {
			printVersion(outputStream);
			commandLine.printHelp(outputStream, getCommandSample() + " [options]");
			outputStream.print("Issues management: ");
			outputStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
			outputStream.flush();
			return 0;
		}
		EasyJaSubInputFromCommand input = null;
		try {
			input = new EasyJaSubInputFromCommand(commandLine);
			if (input.getDefaultFileNamePrefix() == null) {
				throw new EasyJaSubException("No input file specified");
			}
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
			outputStream.println();
			outputStream.flush();
			printVersion(errorStream);
			errorStream.println("Command error:");
			errorStream.println(ex.getMessage());
			suggestHelp(errorStream);
			errorStream.flush();
			return -2;
		}
		catch (Exception ex) {
			outputStream.println();
			outputStream.flush();
			printVersion(errorStream);
			errorStream.println("Unexpected command error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.print("Issues management: ");
			errorStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
			errorStream.flush();
			return -100;
		}
		try {
			return new EasyJaSub().run(input, new EasyJaSubConsole(
					outputStream, errorStream, commandLine.getVerbose()));
		}
		catch (EasyJaSubException ex) {
			outputStream.println();
			outputStream.flush();
			printVersion(errorStream);
			errorStream.println("Execution error:");
			errorStream.println(ex.getMessage());
			errorStream.flush();
			return -3;
		}
		catch (Exception ex) {
			outputStream.println();
			outputStream.flush();
			printVersion(errorStream);
			errorStream.println("Unexpected error:");
			errorStream.println(ex.getMessage());
			ex.printStackTrace(errorStream);
			errorStream.println("This error may be a problem in the program, please report it.");
			errorStream.print("Issues management: ");
			errorStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
			errorStream.flush();
			return -100;
		}
	}

	private void suggestHelp(PrintWriter errorStream) {
		errorStream.println("Run " + getCommandSample() + " -h for help");
	}

	private String getCommandSample() {
		String usage = 
				EasyJaSubProgramLocation.isExe() ? 
				EasyJaSubProgramLocation.getName() :		
				"java -jar " + EasyJaSubProgramLocation.getLocationStr();
		return usage;
	}

	private void printVersion(PrintWriter outputStream) {
		outputStream.print(EasyJaSubCmdProperty.getName());
		outputStream.print(" ");
		outputStream.print(EasyJaSubCmdProperty.getVersion());
		outputStream.print(" ");
		outputStream.print(EasyJaSubCmdProperty.getUrl());
		outputStream.print(" built on ");
		outputStream.print(EasyJaSubCmdProperty.getDate());
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
