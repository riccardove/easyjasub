package com.github.riccardove.easyjasub;

import java.io.PrintWriter;

/**
 * Default entry point for the easyjasub console application
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
		    int result = new EasyJaSubCommandLineApp().run(args, 
		    	new PrintWriter(System.out, true), 
		    	new PrintWriter(System.err, true));
			System.exit(result);
		} catch (Exception e) {
			System.err.println("Unhandled application error:");
			e.printStackTrace(System.err);
			System.exit(-1000);
		}
    }
}
