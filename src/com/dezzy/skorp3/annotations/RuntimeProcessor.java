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

/**
 * NOTE: This class is not related to the compile-time annotation processor interface, Processor.
 * 
 * This is the parent class for any Runtime Annotation Processor in the annotations package.
 * 
 * Rules for creating new Annotation Processors:
 * -Create a separate package in com.dezzy.skorp3.annotations.
 * -Create your annotation in this package and make sure that the retention policy is set to RUNTIME.
 * -Create a new class, name it like "AnnotationameProcessor" and put it in your new package.
 * -Extend RuntimeProcessor and implement process().
 * -process() should use RuntimeProcessor's ifThenApply(), which looks through a list of all classes
 * and checks if a specified predicate is true. (For example, if the class is marked by your annotation,
 * print some message to Logger.)
 * -Go to the Processors class and add the line, processAll(Annotationname.class).
 * 
 * It is also worth noting that when creating a new package, add it to the file "packages.txt"
 * so that classes inside the package can be loaded.
 * 
 * @author Dezzmeister
 *
 */
public abstract class RuntimeProcessor {
	
	public RuntimeProcessor() {
		
	}
	
	/**
	 * Searches a package for loaded classes and returns them in a list.
	 * DOES NOT SEARCH SUB-PACKAGES.
	 * 
	 * @param packageName fully qualified package name
	 * @return A list of Classes in the package
	 */
	static List<Class<?>> getClassesForPackage(String packageName) {
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
			System.err.println("Error loading Class URLs for com.dezzy.skorp3.annotations.UntestedProcessor");
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
    static List<Class<?>> getAllClasses() {
		List<String> packages = Load.load("packages.txt").collect(Collectors.toList());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		packages.forEach(s -> classes.addAll(getClassesForPackage(s)));
		return classes;
	}
	
	/**
	 * Iterates through a list of all Classes that have been loaded by the Main Thread's ClassLoader.
	 * For each class, if the specified predicate is true, the specified action is performed.
	 * 
	 * @param predicate Condition to check for each Class
	 * @param actor Void function to act upon Class if predicate returns true
	 */
	protected void ifThenApply(Predicate<? super Class<?>> predicate, Consumer<? super Class<?>> action) {
		for (Class<?> c : Processors.LOADED_CLASSES) {
			if (predicate.test(c)) {
				action.accept(c);
			}
		}
	}
	
	/**
	 * Process an annotation. Should make a call to ifThenApply().
	 */
	public abstract void process();
}
