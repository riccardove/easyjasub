package com.github.riccardove.easyjasub;

/**
 * Entry point for a console application
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
		    int result = EasyJaSub.run(args);
			System.exit(result);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
    }
}
