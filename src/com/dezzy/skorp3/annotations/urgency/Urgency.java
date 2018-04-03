package com.dezzy.skorp3.annotations.urgency;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Applicable to a method or type (class, interface, etc.). Value can range from 0-4, inclusive. Urgency of 0
 * is the highest and will cause the program to terminate with a message to both the log and the standard console.
 * Urgency decreases from 0 to 4, with 4 begin the least urgent. All levels create warnings in the log.
 * 
 * @author Dezzmeister
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Urgency {
	int value() default 0; //Terminate the program by default
}
