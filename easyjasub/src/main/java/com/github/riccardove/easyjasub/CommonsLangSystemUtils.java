package com.github.riccardove.easyjasub;

import org.apache.commons.lang3.SystemUtils;

class CommonsLangSystemUtils {
	public static boolean isWindows() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	public static boolean isUnix() {
		return SystemUtils.IS_OS_UNIX;
	}
}
