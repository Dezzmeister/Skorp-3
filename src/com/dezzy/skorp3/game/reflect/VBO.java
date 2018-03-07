package com.dezzy.skorp3.game.reflect;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * Still in the works
 * 
 * @author Dezzmeister
 *
 */
@Retention(RUNTIME)
public @interface VBO {
	String value() default "mainVBO";
}
