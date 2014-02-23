package com.github.riccardove.easyjasub;

import java.io.File;
import java.security.CodeSource;

class EasyJaSubProgramLocation {

	static {
		String locationStr = null;
		String name = null;
		try {
			CodeSource source = EasyJaSubProgramLocation.class.getProtectionDomain().getCodeSource();
			File file = new File(source.getLocation().toURI());
			locationStr = file.getCanonicalPath();
			name = file.getName();
		}
		catch (Throwable ex) {

		}
		LocationStr = locationStr;
		Name = name;
	}
	
	private static final String LocationStr;
	
	public static String getLocationStr() {
		return LocationStr;
	}
	
	private static final String Name;
	
	public static String getName() {
		return Name;
	}
	
	public static boolean isExe() {
		return Name.endsWith(".exe");
	}
}
