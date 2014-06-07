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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;

class EasyJaSubCommandLineApp {
	public EasyJaSubCommandLineApp(EasyJaSubCommandLine commandLine) {
		this.commandLine = commandLine;
	}

	private final EasyJaSubCommandLine commandLine;

	public int run(String[] args, PrintWriter outputStream,
			PrintWriter errorStream) {
		if (!commandLine.parse(args)) {
			return error(errorStream);
		}
		if (commandLine.isHelp()) {
			return printHelp(outputStream);
		}
		EasyJaSubInputFromCommand input = null;
		try {
			input = new EasyJaSubInputFromCommand(commandLine);
			if (input.getDefaultFileNamePrefix() == null) {
				throw new EasyJaSubException("No input file specified");
			}
			printInput(outputStream, input);
		} catch (EasyJaSubException ex) {
			return commandError(outputStream, errorStream, ex);
		} catch (Exception ex) {
			return commandUnexpectedError(outputStream, errorStream, ex);
		}
		return execute(outputStream, errorStream, input);
	}

	private int commandUnexpectedError(PrintWriter outputStream,
			PrintWriter errorStream, Exception ex) {
		outputStream.println();
		outputStream.flush();
		printVersion(errorStream);
		errorStream.println("Unexpected command error:");
		errorStream.println(ex.getMessage());
		ex.printStackTrace(errorStream);
		errorStream
				.println("This error may be a problem in the program, please report it.");
		errorStream.print("Issues management: ");
		errorStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
		errorStream.flush();
		return -100;
	}

	private int commandError(PrintWriter outputStream, PrintWriter errorStream,
			EasyJaSubException ex) {
		outputStream.println();
		outputStream.flush();
		printVersion(errorStream);
		errorStream.println("Command error:");
		errorStream.println(ex.getMessage());
		suggestHelp(errorStream);
		errorStream.flush();
		return -2;
	}

	private int error(PrintWriter errorStream) {
		printVersion(errorStream);
		errorStream.println("Command invocation error:");
		errorStream.println(commandLine.getMessage());
		suggestHelp(errorStream);
		errorStream.flush();
		return -1;
	}

	private int printHelp(PrintWriter outputStream) {
		printVersion(outputStream);
		outputStream.println();
		outputStream.println(EasyJaSubCmdLicense.getLicense());
		outputStream.println();
		outputStream.println(EasyJaSubLicense.getLicense());
		outputStream.println();
		outputStream.println();

		commandLine.printHelp(outputStream, getCommandSample()
				+ " [options]", null, null);
		outputStream.println();
		outputStream.print("Issues management: ");
		outputStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
		outputStream.flush();
		return 0;
	}

	private void printInput(PrintWriter outputStream,
			EasyJaSubInputFromCommand input) throws IOException {
		outputStream.println("Processing "
				+ input.getDefaultFileNamePrefix());
		URI baseDirectory = new File(SystemProperty.getUserDir()).toURI();
		printFile(outputStream, "Video file: ", input.getVideoFile(),
				baseDirectory);
		printFile(outputStream, "Japanese subtitles file: ",
				input.getJapaneseSubFile(), baseDirectory);
		printFile(outputStream, "Translated subtitles file: ",
				input.getTranslatedSubFile(), baseDirectory);
		printFile(outputStream, "CSS file: ", input.getCssFile(),
				baseDirectory);
		printFile(outputStream, "HTML intermediate directory: ",
				input.getOutputHtmlDirectory(), baseDirectory);
		printFile(outputStream, "Japanese text file: ",
				input.getOutputJapaneseTextFile(), baseDirectory);
		printFile(outputStream, "BDN XML file: ", input.getBdnXmlFile(),
				baseDirectory);
		printFile(outputStream, "IDX file: ", input.getOutputIdxFile(),
				baseDirectory);
		if (input.getUrl() != null) {
			outputStream.println("URL: " + input.getUrl().toString());
		}
		outputStream.flush();
	}

	private int execute(PrintWriter outputStream, PrintWriter errorStream,
			EasyJaSubInputFromCommand input) {
		try {
			if (input.getUrl() != null) {
				return sendInputToEasyJaSubServlet(outputStream,
						input.getUrl(), input);
			} else {
				return runEasyJaSub(outputStream, errorStream, input);
			}
		} catch (EasyJaSubException ex) {
			executionError(outputStream, errorStream, ex);
			return -3;
		} catch (Exception ex) {
			unexpectedError(outputStream, errorStream, ex);
			return -100;
		}
	}

	private int sendInputToEasyJaSubServlet(PrintWriter outputStream, URL url,
			EasyJaSubInput input) throws EasyJaSubException {
		EasyJaSubSendInputHttpConnection connection = null;
		try {
			connection = EasyJaSubSendInputHttpConnection.open(url);
		} catch (IOException e) {
			throw new EasyJaSubException("Can not connect to URL "
					+ url.toString() + ": " + e.getLocalizedMessage());
		}
		try {
			connection.execute(input);
		} catch (IOException e) {
			throw new EasyJaSubException("Error sending request to URL "
					+ url.toString() + ": " + e.getLocalizedMessage());
		}
		outputStream.println("Response:");
		try {
			BufferedReader s = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			for (String line = s.readLine(); line != null; line = s.readLine()) {
				outputStream.println(line);
			}
		} catch (IOException e) {
			throw new EasyJaSubException(
					"Error sending reading response from URL " + url.toString()
							+ ": " + e.getLocalizedMessage());
		} finally {
			outputStream.println("End of response.");
			outputStream.flush();
		}
		return 0;
	}

	private int runEasyJaSub(PrintWriter outputStream, PrintWriter errorStream,
			EasyJaSubInput input) throws EasyJaSubException {
		return new EasyJaSub().run(input, new EasyJaSubConsole(outputStream,
				errorStream, commandLine.getVerbose()));
	}

	private void unexpectedError(PrintWriter outputStream,
			PrintWriter errorStream, Exception ex) {
		outputStream.println();
		outputStream.flush();
		printVersion(errorStream);
		errorStream.println("Unexpected error:");
		errorStream.println(ex.getMessage());
		ex.printStackTrace(errorStream);
		errorStream
				.println("This error may be a problem in the program, please report it.");
		errorStream.print("Issues management: ");
		errorStream.println(EasyJaSubCmdProperty.getIssuesManagementUrl());
		errorStream.flush();
	}

	private void executionError(PrintWriter outputStream,
			PrintWriter errorStream, EasyJaSubException ex) {
		outputStream.println();
		outputStream.flush();
		printVersion(errorStream);
		errorStream.println("Execution error:");
		errorStream.println(ex.getMessage());
		errorStream.flush();
	}

	private void suggestHelp(PrintWriter errorStream) {
		errorStream.println("Run " + getCommandSample() + " -h for help");
	}

	private String getCommandSample() {
		String usage = EasyJaSubProgramLocation.isExe() ? EasyJaSubProgramLocation
				.getName() : "java -jar "
				+ EasyJaSubProgramLocation.getLocationStr();
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

	private static String toRelativePath(File file, URI baseDirectory) {
		return baseDirectory.relativize(file.toURI()).getPath();
	}

	private static void printFile(PrintWriter outputStream, String message,
			File file, URI baseDirectory) throws IOException {
		if (file != null) {
			outputStream.print(message);
			outputStream.print(toRelativePath(file, baseDirectory));
			if (!file.exists()) {
				outputStream.print('*');
			}
			outputStream.println();
		}
	}
}
