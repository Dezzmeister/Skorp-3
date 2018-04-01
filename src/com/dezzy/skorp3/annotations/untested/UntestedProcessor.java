package com.dezzy.skorp3.annotations.untested;

import java.lang.reflect.Method;

import com.dezzy.skorp3.annotations.RuntimeProcessor;
import com.dezzy.skorp3.log.Logger;

/**
 * A class to process Untested annotations and print which classes/methods need to be tested.
 * 
 * @author Dezzmeister
 *
 */
public class UntestedProcessor extends RuntimeProcessor {
	
	public UntestedProcessor() {
		
	}
	
	@Override
	public void process() {
		ifThenApply(c -> true, this::processClass);
	}
	
	private void processClass(Class<?> clazz) {
		if (clazz.isAnnotationPresent(Untested.class)) {
			System.out.println(message(clazz));
			Logger.log(message(clazz));
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(Untested.class)) {
				System.out.println(message(m));
				Logger.log(message(m));
			}
		}
	}
	
	private String message(Class<?> clazz) {
		return "WARNING: " + clazz.getName() + " needs testing!";
	}
	
	private String message(Method method) {
		return "WARNING: Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " needs testing!";
	}
}
