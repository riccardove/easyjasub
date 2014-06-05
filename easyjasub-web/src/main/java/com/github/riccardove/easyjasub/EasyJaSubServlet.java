package com.github.riccardove.easyjasub;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EasyJaSubServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936273262291783254L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>Hello Servlet</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
	}
}
