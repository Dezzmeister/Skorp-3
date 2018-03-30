package com.dezzy.skorp3.test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * A class to process Untested annotations and print which classes/methods need to be tested.
 * 
 * @author Dezzmeister
 *
 */
public class UntestedProcessor implements Processor {
	static {
		List<Class<?>> classes = getClassesForPackage("com.dezzy.skorp3");
		System.out.println(classes.size());
		for (Class<?> c : classes) {
			System.out.println(c.getName());
		}
	}
	
	
	private static List<Class<?>> getClassesForPackage(String packageName) {
		List<File> directories = new ArrayList<File>();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = loader.getResources(path);
			while (resources.hasMoreElements()) {
				System.out.println("j0j");
				directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
			}
		} catch(Exception e) {
			System.err.println("Error loading Classes in com.dezzy.skorp3.test.UntestedProcessor");
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
							
						}
					}
				}
			}
		}
		
		return classes;
	}
	
	public UntestedProcessor() {
		
	}

	@Override
	public Iterable<? extends Completion> getCompletions(Element arg0, AnnotationMirror arg1, ExecutableElement arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getSupportedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ProcessingEnvironment arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
		// TODO Auto-generated method stub
		System.out.println("lol");
		return false;
	}
}
