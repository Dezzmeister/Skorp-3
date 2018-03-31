package com.dezzy.skorp3.annotations;

import java.lang.reflect.Method;

import com.dezzy.skorp3.annotations.untested.Untested;

public class Processors {
	static {
		processAll(Untested.class);
	}
	
	public static void processAll(Class<?> ... classes) {
		for (Class<?> c : classes) {
			try {
				Class<?> processorClass = Class.forName(c.getName() + "Processor");
				Method process = processorClass.getMethod("process");
				Object processor = processorClass.getDeclaredConstructor().newInstance();
				process.invoke(processor);
			} catch (Exception e) {
				//e.printStackTrace();
				System.err.println("Error processing annotations in com.dezzy.skorp3.annotations.Processors.");
			}
		}
	}
	
	public static void activate() {}
}
