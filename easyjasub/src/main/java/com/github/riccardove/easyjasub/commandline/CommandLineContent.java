package com.github.riccardove.easyjasub.commandline;

import java.util.List;

import org.apache.commons.cli.CommandLine;

class CommandLineContent {

	private CommandLine line;

	public CommandLineContent(CommandLine line) {
		this.line = line;
	}

	public boolean hasOption(String opt) {
		return line.hasOption(opt);
	}

	public String getOptionValue(String opt) {
		return line.getOptionValue(opt);
	}

	@SuppressWarnings("rawtypes")
	public List getArgList() {
		return line.getArgList();
	}

}
