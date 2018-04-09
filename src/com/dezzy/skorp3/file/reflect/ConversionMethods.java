package com.dezzy.skorp3.file.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * A class containing methods to convert a String to
 * <p>
 * 	-Integer
 * <p>
 * 	-Double
 * <p>
 * 	-Character
 * <p>
 * 	-Float
 * <p>
 * 	-Short
 * <p>
 * 	-Long
 * <p>
 * 	-Boolean
 * <p>
 * 	-Byte
 * <p>
 *	-String (for easier reflection)
 *<p>
 * ConversionMethods knows its own methods, and it should be used with reflection.
 * 
 * @author Dezzmeister
 * @see com.dezzy.skorp3.file.Config
 *
 */
public final class ConversionMethods {
	private static Map<String,Method> conversions = new HashMap<String,Method>();
	
	static {
		try {
			for (Method method : Class.forName("com.dezzy.skorp3.file.reflect.ConversionMethods").getMethods()) {
				if (Modifier.isStatic(method.getModifiers()) && method.getName().indexOf("parse")!=-1) {
					conversions.put(method.getName(), method);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * There is no reason to instantiate ConversionMethods.
	 */
	private ConversionMethods() {
		
	}
	
	public static int parseInteger(String s) {
		return Integer.parseInt(s);
	}
	
	public static double parseDouble(String s) {
		return Double.parseDouble(s);
	}
	
	public static char parseCharacter(String s) {
		return s.charAt(0);
	}
	
	public static float parseFloat(String s) {
		return Float.parseFloat(s);
	}
	
	public static short parseShort(String s) {
		return Short.parseShort(s);
	}
	
	public static long parseLong(String s) {
		return Long.parseLong(s);
	}
	
	public static boolean parseBoolean(String s) {
		return Boolean.parseBoolean(s);
	}
	
	public static byte parseByte(String s) {
		return Byte.parseByte(s);
	}
	
	public static String parseString(String s) {
		return s;
	}
	
	public static Map<String,Method> getConversionMethods() {
		return conversions;
	}
	
	public static int activate() {
		return 0;
	}
}
