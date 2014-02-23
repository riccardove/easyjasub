/*
 * Copyright 2014 Riccardo Vestrini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		    	new PrintWriter(System.out), 
		    	new PrintWriter(System.err));
			System.exit(result);
		} catch (Exception e) {
			System.err.println("Unhandled application error:");
			e.printStackTrace(System.err);
			System.exit(-1000);
		}
    }
}
