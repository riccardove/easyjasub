package com.github.riccardove.easyjasub;

class SystemProperty {
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}
	
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}
}
