package com.dezzy.skorp3.log;

import java.io.File;
import java.io.PrintStream;

public class Logger {
	private static File outputFile = new File("log.txt");
	public static PrintStream log;

	static {
		try {
			log = new PrintStream(outputFile);
		} catch(Exception e) {
			
		}
		log.println("Time in ms\tEvent");
		log.println();
		log("Log created");
	}
	
	public static void log(Object ... objects) {
		log.print(System.currentTimeMillis() + "\t");
		for (Object o : objects) {
			log.print(o);
		}
		log.println();
	}
}
