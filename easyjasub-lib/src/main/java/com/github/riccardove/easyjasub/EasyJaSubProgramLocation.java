package com.github.riccardove.easyjasub;

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
