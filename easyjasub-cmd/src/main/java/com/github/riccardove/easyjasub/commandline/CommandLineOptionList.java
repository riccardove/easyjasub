package com.github.riccardove.easyjasub.commandline;

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
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * Support for parsing command line options, using Apache CLI library
 */
public class CommandLineOptionList {

	public CommandLineOptionList() {
		options = new Options();
		optionsOrder = new HashMap<String, Integer>();
	}

	private final Options options;
	private final HashMap<String, Integer> optionsOrder;

	public void addOption(String opt, String longOpt, String description) {
		Option option = new Option(opt, longOpt, false, description);
		add(opt, option);
	}

	private void add(String opt, Option option) {
		options.addOption(option);
		optionsOrder.put(opt, optionsOrder.size());
	}

	public void addOption(String opt, String longOpt, String description,
			String argName) {
		Option option = new Option(opt, longOpt, true, description);
		option.setArgName(argName);
		add(opt, option);
	}

	public CommandLineContent parse(String[] args) throws Exception {
		CommandLineParser parser = new PosixParser();
		CommandLine line = parser.parse(options, args);
		return new CommandLineContent(line);
	}

	public void printHelp(PrintWriter stream, String usage, String header,
			String footer) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(new Comparator<Option>() {
			@Override
			public int compare(Option opt1, Option opt2) {
				return (int) Math.signum(optionsOrder.get(opt1.getOpt())
						- optionsOrder.get(opt2.getOpt()));
			}
		});
		formatter.printHelp(stream, HelpFormatter.DEFAULT_WIDTH, usage, header,
				options, HelpFormatter.DEFAULT_LEFT_PAD,
				HelpFormatter.DEFAULT_DESC_PAD, footer);
	}

}
