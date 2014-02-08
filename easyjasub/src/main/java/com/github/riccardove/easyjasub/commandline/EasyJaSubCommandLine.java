package com.github.riccardove.easyjasub.commandline;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.github.riccardove.easyjasub.EasyJaSubInputCommand;
import com.github.riccardove.easyjasub.Phases;

public class EasyJaSubCommandLine implements EasyJaSubInputCommand {
	private static final String HELP = "h";
	private static final String VI = "vi";
	private static final String JA = "ja";
	private static final String TR = "tr";
	private static final String NJ = "nj";
	private static final String TRL = "trl";
	private static final String IDX = "oidx";
	private static final String HTML = "ohtml";
	private static final String BDN = "obdn";
	private static final String PH = "p";
	private static final String WK = "wk";

	public EasyJaSubCommandLine() {
		list = new CommandLineOptionList();
		String phases = StringUtils.join(",", Phases.values());
		list.addOption(PH, "phases",
				"List of steps to execute, to partially execute them; "
						+ "comma separated. Possible values are: " + phases,
				"comma-separated-list");
		list.addOption(VI, "video",
				"Sets file as reference for video and audio", "file");
		list.addOption(
				JA,
				"japanese-sub",
				"Read japanese subtitles from file. "
						+ "If not specified files with the same name of video file and of supported text subtitle type are searched.",
				"file");
		list.addOption(
				TR,
				"translated-sub",
				"Read translated subtitles from file. "
						+ "If not specified files with the same name of video file and of supported text subtitle type are searched.",
				"file");
		list.addOption(
				NJ,
				"nihongo-jtalk",
				"Read text analysys produced with http://nihongo.j-talk.com from file. "
						+ "If not specified HTML files with the same name of video file are searched.",
				"file");
		list.addOption(
				TRL,
				"translated-sub-language",
				"Sets language (two letter ISO code) as language used for translation. "
						+ "By default reads it from the translated subtitle file.",
				"language");
		list.addOption(IDX, "output-idx",
				"Writes produced IDX/SUB files in directory", "directory");
		list.addOption(HTML, "output-html",
				"Writes HTML intermediate files in directory", "directory");
		list.addOption(BDN, "output-bdmxml",
				"Writes BDN/XML intermediate files in directory", "directory");
		list.addOption(WK, "wkhtmltoimage",
				"Command to execute wkhtmltoimage program", "command");
		list.addOption(HELP, "help", "Displays help");
	}

	private final CommandLineOptionList list;
	private String message;
	private boolean isHelp;

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isHelp() {
		return isHelp;
	}

	@Override
	public String getVideoFileName() {
		return videoFileName;
	}

	@Override
	public String getJapaneseSubFileName() {
		return japaneseSubFileName;
	}

	@Override
	public String getTranslatedSubFileName() {
		return translatedSubFileName;
	}

	@Override
	public String getNihongoJtalkHtmlFileName() {
		return nihongoJtalkHtmlFileName;
	}

	@Override
	public String getTranslatedSubLanguage() {
		return translatedSubLanguage;
	}

	@Override
	public String getOutputIdxDirectory() {
		return outputIdxDirectory;
	}

	@Override
	public String getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	@Override
	public String getOutputBdnDirectory() {
		return outputBdnDirectory;
	}

	@Override
	public String getWkhtmltoimage() {
		return wkhtmltoimage;
	}

	@Override
	public Set<Phases> getPhases() {
		return phases;
	}

	private String videoFileName;
	private String japaneseSubFileName;
	private String translatedSubFileName;
	private String nihongoJtalkHtmlFileName;
	private String translatedSubLanguage;
	private String outputIdxDirectory;
	private String outputHtmlDirectory;
	private String outputBdnDirectory;
	private String wkhtmltoimage;

	private HashSet<Phases> phases;

	public boolean parse(String[] args) {
		try {
			CommandLineContent cm = list.parse(args);
			isHelp = cm.hasOption(HELP);
			videoFileName = cm.getOptionValue(VI);
			japaneseSubFileName = cm.getOptionValue(JA);
			translatedSubFileName = cm.getOptionValue(TR);
			nihongoJtalkHtmlFileName = cm.getOptionValue(NJ);
			translatedSubLanguage = cm.getOptionValue(TRL);
			outputIdxDirectory = cm.getOptionValue(IDX);
			outputHtmlDirectory = cm.getOptionValue(HELP);
			outputBdnDirectory = cm.getOptionValue(BDN);
			wkhtmltoimage = cm.getOptionValue(WK);
			String phasesStr = cm.getOptionValue(PH);
			for (Object invalidArg : cm.getArgList()) {
				addErrorMessage(invalidArg);
			}
			if (phasesStr != null) {
				for (String phaseStr : StringUtils.split(",", phasesStr)) {
					try {
						Phases phase = Phases.valueOf(phaseStr.trim());
						if (phases == null) {
							phases = new HashSet<Phases>();
						}
						phases.add(phase);
					} catch (Exception ex) {
						addErrorMessage(phaseStr);
					}
				}
			}
		} catch (Exception ex) {
			message = ex.getMessage();
		}
		if (!isHelp && message != null) {
			return false;
		}
		return true;
	}

	private void addErrorMessage(Object invalidArg) {
		if (message == null) {
			message = "Unrecognized arguments: " + invalidArg.toString();
		} else {
			message += " " + invalidArg.toString();
		}
	}

	public void printHelp() {
		list.printHelp();
	}
}
