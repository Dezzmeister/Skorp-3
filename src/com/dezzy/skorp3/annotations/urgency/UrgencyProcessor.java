package com.dezzy.skorp3.annotations.urgency;

import java.lang.reflect.Method;

import com.dezzy.skorp3.annotations.RuntimeProcessor;
import com.dezzy.skorp3.log.Logger;

public class UrgencyProcessor extends RuntimeProcessor {

	@Override
	public void process() {
		applyAll(this::processClass);
	}
	
	private void processClass(Class<?> clazz) {
		if (clazz.isAnnotationPresent(Urgency.class)) {
			String elementName = clazz.getName();
			int annotationValue = clazz.getAnnotation(Urgency.class).value();
			
			log(annotationValue, elementName, null);
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(Urgency.class)) {
				
				String elementName = m.getName();
				int annotationValue = m.getAnnotation(Urgency.class).value();
				String declaringClassName = m.getDeclaringClass().getName();
				
				log(annotationValue, elementName, declaringClassName);
			}
		}
	}
	
	/**
	 * Logs an urgency warning to the console. If the Urgency value is zero, terminates the program.
	 * 
	 * @param urgencyValue value obtained from Urgency annotation
	 * @param elementName name of Method or Class
	 * @param declaringClassName a Class if elementName is a method, null if not
	 */
	private void log(int urgencyValue, String elementName, String declaringClassName)	{
		String opt = (declaringClassName == null) ? "" : " in declaring class " + declaringClassName;
		
		switch (urgencyValue) {
		case 0:
			Logger.warn(elementName + opt + " MUST to be fixed/completed before application can run! Program terminating.");
			System.out.println("Program terminating due to Urgency of " + elementName + opt + ".");
			System.exit(0);
			break;
		case 1:
			Logger.warn(elementName + opt + " is important and NEEDS to be fixed/completed now!");
			break;
		case 2:
			Logger.warn(elementName + opt + " is important and SHOULD be fixed/completed!");
			break;
		case 3:
			Logger.warn(elementName + opt + " is important and SHOULD be fixed/completed soon!");
			break;
		case 4:
			Logger.warn(elementName + opt + " should be fixed/completed soon!");
			break;
		default:
			Logger.warn("invalid @Urgency value at " + elementName + opt);
			break;
		}
	}
}
