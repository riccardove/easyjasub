package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-web
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
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EasyJaSubServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936273262291783254L;

	public EasyJaSubServlet() {
		main = new EasyJaSub();
	}

	private final EasyJaSub main;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter writer = response.getWriter();

		try {
			EasyJaSubInput input = deserializeInputCommand(request);
			runCommand(input);
		} catch (EasyJaSubException ex) {
			writer.println("<h1>Error</h1>");
			writer.println("Error: " + ex.getMessage());
		} catch (Throwable ex) {
			writer.println("<h1>Unhandled Error</h1>");
			writer.println("Error: " + ex.getMessage());
			ex.printStackTrace(writer);
		}
	}

	private void runCommand(EasyJaSubInput input) throws EasyJaSubException {
		main.run(input, new Observer());
	}

	private EasyJaSubInput deserializeInputCommand(HttpServletRequest request)
			throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(
				request.getInputStream());
		EasyJaSubInput input = ((EasyJaSubInput) in.readObject());
		return input;
	}

	private static class Observer extends EasyJaSubObserverBase {

	}
}
