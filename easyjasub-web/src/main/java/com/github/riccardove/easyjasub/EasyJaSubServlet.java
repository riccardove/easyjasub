package com.github.riccardove.easyjasub;

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
