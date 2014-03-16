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
import java.util.Properties;

/**
 * Access to the properties from version.properties resource file
 */
public class EasyJaSubCmdProperty {

	static {
		String name = "unknownname";
		String version = "unknownversion";
		String date = "unknowndate";
		String issues = null;
		String url = null;
		InputStream stream = EasyJaSubCmdProperty.class
				.getResourceAsStream("easyjasub-cmd.properties");
	    if (stream != null) {
			Properties properties = new Properties();
			try {
				properties.load(stream);
				name = properties.getProperty("name", name);
				date = properties.getProperty("date", date);
				version = properties.getProperty("version", version);
				issues = properties.getProperty("issues", issues);
				url = properties.getProperty("url", url);
				stream.close();
			} catch (IOException e) {
				stream = null;
			}
	    }
	    Name = name;
	    Date = date;
	    Version = version;
	    IssuesManagementUrl = issues;
	    Url = url;
	}

	private static final String Name;
	private static final String Version;
	private static final String Date;
	private static final String IssuesManagementUrl;
	private static final String Url;
	
	public static String getName() {
		return Name;
	}
	
	public static String getVersion() {
		return Version;
	}
	
	public static String getDate() {
		return Date;
	}
	
	public static String getIssuesManagementUrl() {
		return IssuesManagementUrl;
	}
	
	public static String getUrl() {
		return Url;
	}
}
