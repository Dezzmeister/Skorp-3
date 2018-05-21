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
			System.err.println("Log could not be created.");
			e.printStackTrace();
		}
		log.println("Time in ns\tEvent");
		log.println();
		log("Log created");
	}
	
	public static void log(Object ... objects) {
		log.print(System.nanoTime() + "\t");
		for (Object o : objects) {
			log.print(o);
		}
		log.println();
	}
	
	public static void warn(Object ... objects) {
		log.print(System.nanoTime() + "\tWARNING: ");
		for (Object o : objects) {
			log.print(o);
		}
		log.println();
	}
}
