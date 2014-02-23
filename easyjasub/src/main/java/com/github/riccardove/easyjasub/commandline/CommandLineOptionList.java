package com.github.riccardove.easyjasub.commandline;

import java.io.PrintWriter;
import java.util.*;

import org.apache.commons.cli.*;

class CommandLineOptionList {

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
    
    public void printHelp(PrintWriter stream,
            String usage,
    		String header, String footer) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptionComparator(new Comparator<Option>() {
            @Override
            public int compare(Option opt1, Option opt2) {
                return (int) Math.signum(optionsOrder.get(opt1.getOpt()) - optionsOrder.get(opt2.getOpt()));
            }
        });
        formatter.printHelp(stream, HelpFormatter.DEFAULT_WIDTH,
        		usage, header, options, HelpFormatter.DEFAULT_LEFT_PAD,
        		HelpFormatter.DEFAULT_DESC_PAD, footer);
    }

}
