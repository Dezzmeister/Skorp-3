package com.dezzy.skorp3.annotations;

import java.util.List;

import com.dezzy.skorp3.annotations.untested.Untested;
import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.log.Logger;

/**
 * All runtime annotation processors should be registered in this class
 * using processAll.
 * 
 * @author Dezzmeister
 *
 */
public class Processors {
	static final List<Class<?>> LOADED_CLASSES = RuntimeProcessor.getAllClasses();
	
	static {
		processAll(Untested.class);
		processAll(Urgency.class);
	}
	
	public static void processAll(Class<?> ... classes) {
		for (Class<?> c : classes) {
			try {
				Class<?> processorClass = Class.forName(c.getName() + "Processor");
				RuntimeProcessor processor = (RuntimeProcessor) processorClass.getDeclaredConstructor().newInstance();
				processor.process();
			} catch (Exception e) {
				e.printStackTrace();
				e.printStackTrace(Logger.log);
				System.err.println("Error processing annotations in com.dezzy.skorp3.annotations.Processors.");
			}
		}
	}
	
	public static void activate() {}
}
