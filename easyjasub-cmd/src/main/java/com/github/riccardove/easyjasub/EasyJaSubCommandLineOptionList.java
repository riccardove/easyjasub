package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-cmd
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
import java.util.HashMap;

import com.github.riccardove.easyjasub.commandline.CommandLineContent;
import com.github.riccardove.easyjasub.commandline.CommandLineOptionList;

/**
 * Handles command line options and parsing
 */
class EasyJaSubCommandLineOptionList {

	public EasyJaSubCommandLineOptionList() {
		optionIndexToShortName = new HashMap<EasyJaSubInputOption, String>();
		optionList = new CommandLineOptionList();
	}

	public void addOption(EasyJaSubInputOption optionIndex, String shortName,
			String longName) {
		optionIndexToShortName.put(optionIndex, shortName);
		optionList.addOption(shortName, longName,
				getOptionDescription(optionIndex));
	}

	private String getOptionDescription(EasyJaSubInputOption optionIndex) {
		return EasyJaSubProperty.getOptionDescription(optionIndex);
	}

	/**
	 * Register an option with an argument
	 */
	public void addOption(EasyJaSubInputOption optionIndex, String shortName,
			String longName, String argumentDescription) {
		optionIndexToShortName.put(optionIndex, shortName);
		optionList.addOption(shortName, longName,
				getOptionDescription(optionIndex), argumentDescription);
	}

	private final HashMap<EasyJaSubInputOption, String> optionIndexToShortName;
	private final CommandLineOptionList optionList;
	private CommandLineContent commandLineContent;

	public void printHelp(PrintWriter stream, String usage, String header,
			String footer) {
		optionList.printHelp(stream, usage, header, footer);
	}

	/**
	 * Parse current command line
	 */
	public void parse(String[] args) throws Exception {
		commandLineContent = optionList.parse(args);
	}

	/**
	 * True if specified option exists in the parsed command line. You must call
	 * parse() before this
	 * 
	 * @param optionIndex
	 * @return
	 */
	public boolean hasOption(EasyJaSubInputOption optionIndex) {
		return commandLineContent.hasOption(getOptionShortName(optionIndex));
	}

	/**
	 * Returns the argument of specified option, or null if the option does not
	 * exist in the parsed command line. You must call parse() before this
	 * 
	 * @param optionIndex
	 * @return
	 */
	public String getOptionValue(EasyJaSubInputOption optionIndex) {
		return commandLineContent
				.getOptionValue(getOptionShortName(optionIndex));
	}

	private String getOptionShortName(EasyJaSubInputOption optionIndex) {
		return optionIndexToShortName.get(optionIndex);
	}
}
