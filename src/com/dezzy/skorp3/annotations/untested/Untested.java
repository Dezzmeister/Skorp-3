package com.dezzy.skorp3.annotations.untested;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Mark types (classes, interfaces, etc.) or methods with this annotation to indicate that they still need some testing.
 * A warning message will be printed to the log and the console for each 
 * type or method marked with this annotation.
 * 
 * @author Dezzmeister
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Untested {

}
