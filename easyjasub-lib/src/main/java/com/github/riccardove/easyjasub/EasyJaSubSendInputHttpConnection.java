package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-lib
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
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class EasyJaSubSendInputHttpConnection {

	public static EasyJaSubSendInputHttpConnection open(URL url)
			throws IOException {
		return new EasyJaSubSendInputHttpConnection(url);
	}

	private EasyJaSubSendInputHttpConnection(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/octet-stream");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
	}

	private final HttpURLConnection connection;

	public void execute(EasyJaSubInput input) throws IOException {
		ObjectOutputStream s = new ObjectOutputStream(
				connection.getOutputStream());
		s.writeObject(input);
		s.flush();
		s.close();
	}

	public InputStream getInputStream() throws IOException {
		return connection.getInputStream();
	}

	public void disconnect() {
		connection.disconnect();
	}
}
