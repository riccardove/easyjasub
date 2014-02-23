package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Access to the properties from version.properties resource file
 */
public class EasyJaSubProperty {

	static {
		String name = "unknownname";
		String version = "unknownversion";
		String date = "unknowndate";
		String issues = null;
		String url = null;
		InputStream stream = EasyJaSubProperty.class.getResourceAsStream("version.properties");
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
