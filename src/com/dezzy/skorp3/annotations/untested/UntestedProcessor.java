package com.dezzy.skorp3.annotations.untested;

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
		ifThenApply(c -> c.isAnnotationPresent(Untested.class),
					c -> {
						 	System.out.println("WARNING: " + c.getName() + " is untested!");
						 	Logger.log("WARNING: " + c.getName() + " is untested!");
					});
	}
}
