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


import java.io.IOException;
import java.io.InputStream;

import com.github.riccardove.easyjasub.commons.CommonsIOUtils;

/**
 * Access to the license text available in easyjasub-cmd_LICENSE.txt resource
 */
public class EasyJaSubCmdLicense {

	static {
		InputStream stream = EasyJaSubCmdLicense.class
				.getResourceAsStream("easyjasub-cmd_LICENSE.txt");
		String license = null;
	    if (stream != null) {
			try {
				license = CommonsIOUtils.streamToString(stream);
			} catch (IOException ex) {
				license = "Error reading license file";
			}
	    }
		License = license;
	}

	private static final String License;
	
	public static String getLicense() {
		return License;
	}
}
