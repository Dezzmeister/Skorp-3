package com.dezzy.skorp3.file;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.log.Logger;

/**
 * Class to load config files and store their contents in HashMaps. T is the value type, example:
 * For a line in the config, "value=5", T would be Integer and calling get("value") would return 5.
 * 
 * @author Dezzmeister
 *
 */
//TODO Add methods that will reset the config file if it has been corrupted.
@Urgency(1)
public class Config<T> {
	private Map<String,T> entries = new HashMap<String,T>();
	private static Map<String, Method> conversions = new HashMap<String,Method>();
	private Class<T> clazz;
	/**
	 * The one conversion method to be used to convert from a String to T.
	 */
	private Method conversionMethod;
	
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
	 * _clazz MUST be T's Class type. If you create a Config<Integer>, then _clazz must be Integer.class.
	 * 
	 * @param path Path to config file
	 * @param _clazz Must be Class of T
	 */
	public Config(String path, Class<T> _clazz) {
		clazz = _clazz;
		findConversionMethod();
		readConfig(path);
		Logger.log("Config created with "+path);
	}
	
	private void findConversionMethod() {
		String fullName = "parse"+clazz.getName();
		try {
			conversionMethod = conversions.get(fullName);
		} catch(Exception e) {
			e.printStackTrace();
			e.printStackTrace(Logger.log);
		}
	}
	
	private void readConfig(String path) {
		Load.load(path).forEach(this::formatAndStoreConfigEntry);
	}
	
	@SuppressWarnings("unchecked")
	public void formatAndStoreConfigEntry(String entry) {
		String entryName = entry.substring(0,entry.indexOf("="));
		String entryValueName = entry.substring(entry.indexOf("=")+1);
		Logger.log("Received config entry "+entryValueName);
		try {
			T value = (T) conversionMethod.invoke(null, entryValueName);
			entries.put(entryName, value);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace(Logger.log);
		}
	}
	
	public T get(String key) {
		return entries.get(key);
	}
	
	/**
	 * Return a converted value. For instance, if you have a Config<String> and you know that some values will be
	 * used as Integers - for instance, a value named "speed," calling <Integer>get("speed",Integer.class) will return the value
	 * for "speed," but as an Integer instead of the declared String.
	 * 
	 * @param key
	 * @param klazz E's Class type (example: Integer.class if E is Integer)
	 * @return parsed value identified by key; null if E is not a "convertable" type.
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String key, Class<E> klazz) {
		String className = klazz.getName();
		String fullName = "parse"+className;
		try {
			Method converter = conversions.get(fullName);
			E value = (E) converter.invoke(null, key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace(Logger.log);
			return null;
		}
	}
}