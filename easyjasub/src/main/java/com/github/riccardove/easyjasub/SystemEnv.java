package com.github.riccardove.easyjasub;

class SystemEnv {
	public static String getWindowsProgramFiles() {
		return System.getenv("ProgramFiles");
	}

	public static String getWindowsProgramFiles32() {
		return System.getenv("ProgramFiles(x86)");
	}
}
