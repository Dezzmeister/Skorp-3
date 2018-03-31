package com.dezzy.skorp3.annotations;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.dezzy.skorp3.file.Load;

public abstract class RuntimeProcessor {
	protected static final List<Class<?>> LOADED_CLASSES = getAllClasses();
	
	public RuntimeProcessor() {
		
	}
	
	protected static List<Class<?>> getClassesForPackage(String packageName) {
		List<File> directories = new ArrayList<File>();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = loader.getResources(path);
			while (resources.hasMoreElements()) {
				directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Error loading Classes for com.dezzy.skorp3.annotations.UntestedProcessor");
		}
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : directories) {
			if (directory.exists()) {
				String[] files = directory.list();
				for (String file : files) {
					if (file.endsWith(".class")) {
						try {
							classes.add(Class.forName(packageName + "." + file.substring(0,file.length()-6)));
						} catch(Exception e) {
							e.printStackTrace();
							System.err.println("Error finding all Classes");
						}
					}
				}
			}
		}
		
		return classes;
	}
	
	/**
	 * Reads packages.txt in the top-level directory and calls getClassesForPackage() on each package.
	 * 
	 * @return A list of all loaded classes in all packages specified by packages.txt
	 */
	protected static List<Class<?>> getAllClasses() {
		List<String> packages = Load.load("packages.txt").collect(Collectors.toList());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		System.out.println(packages.size());
		
		packages.forEach(s -> classes.addAll(getClassesForPackage(s)));
		return classes;
	}
	
	/**
	 * Iterates through a list of Classes returned by getAllClasses().
	 * For each class, if the specified predicate is true, the specified action is performed.
	 * 
	 * @param predicate Condition to check for each Class
	 * @param actor Void function to act upon Class if predicate returns true
	 */
	protected void ifThenApply(Predicate<? super Class<?>> predicate, Consumer<? super Class<?>> action) {
		for (Class<?> c : LOADED_CLASSES) {
			if (predicate.test(c)) {
				action.accept(c);
			}
		}
	}
	
	public abstract void process();
}
