package com.github.riccardove.easyjasub;

class SystemProperty {
	
	static {
		UserDir = System.getProperty("user.dir");
		LineSeparator = System.getProperty("line.separator");
	}
	
	private static final String UserDir;
	private static final String LineSeparator;
	
	public static String getUserDir() {
		return UserDir;
	}
	
	public static String getLineSeparator() {
		return LineSeparator;
	}
}
