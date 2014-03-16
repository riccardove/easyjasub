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

import java.util.List;

import org.apache.commons.cli.CommandLine;

public class CommandLineContent {

	private final CommandLine line;

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
