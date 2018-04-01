package com.dezzy.skorp3.annotations;

import com.dezzy.skorp3.annotations.untested.Untested;

public class Processors {
	static {
		processAll(Untested.class);
	}
	
	public static void processAll(Class<?> ... classes) {
		for (Class<?> c : classes) {
			try {
				Class<?> processorClass = Class.forName(c.getName() + "Processor");
				RuntimeProcessor processor = (RuntimeProcessor) processorClass.getDeclaredConstructor().newInstance();
				processor.process();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error processing annotations in com.dezzy.skorp3.annotations.Processors.");
			}
		}
	}
	
	public static void activate() {}
}
