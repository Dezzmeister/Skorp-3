package com.dezzy.skorp3.net;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * A class to provide functionality for Directives and Directories. A directive is code that will be run when an assciated header is received (via TCP or UDP).
 * A directory holds several directives, connected with a boolean "key." When the boolean "key" evaluates to true, the directory is unlocked and contained directives
 * become available to the client/server. This provides some control over what gets executed, and when. * 
 *   
 * @author Dezzmeister
 *
 */
public class DirectiveContainer {
	//Might not need a StringActor, Consumer<String> MAY work
	private volatile Map<String,Consumer<String>> clientDirectives = new HashMap<String,Consumer<String>>();
	private volatile Map<String,Consumer<String>> serverDirectives = new HashMap<String,Consumer<String>>();
	private volatile Map<BooleanSupplier, NamedMap<String,Consumer<String>>> clientSubDirectories = new HashMap<BooleanSupplier, NamedMap<String,Consumer<String>>>();
	private volatile Map<BooleanSupplier, NamedMap<String,Consumer<String>>> serverSubDirectories = new HashMap<BooleanSupplier, NamedMap<String,Consumer<String>>>();
	
	{
		createClientSubDirectory("main",() -> true);
		createServerSubDirectory("main",() -> true);
	}
	
	public void addConstantClientDirective(String header, Consumer<String> action) {
		clientDirectives.put(header,action);
	}
	
	public void addConstantServerDirective(String header, Consumer<String> action) {
		serverDirectives.put(header, action);
	}
	
	public void executeClientDirective(String header, String full) {
		try {
			clientDirectives.get(header).accept(full);
		} catch (Exception e) {
			
		}
	}
	
	public void executeServerDirective(String header, String full) {
		try {
			serverDirectives.get(header).accept(full);
		} catch (Exception e) {
			
		}
	}
	
	public void createClientSubDirectory(String name, BooleanSupplier activator) {
		clientSubDirectories.put(activator, new NamedMap<String,Consumer<String>>(name));
	}
	
	public void createServerSubDirectory(String name, BooleanSupplier activator) {
		serverSubDirectories.put(activator, new NamedMap<String,Consumer<String>>(name));
	}
	
	public void findAndExecuteClientDirective(String header, String full) {
		if (clientDirectives.get(header)!=null) {
			clientDirectives.get(header).accept(full);
		}
		clientSubDirectories.forEach((b, n) -> {
			if (b.getAsBoolean()) {
				if (n.get(header)!=null) {
					n.get(header).accept(full);
				}
			}
		});
	}
	
	public void findAndExecuteServerDirective(String header, String full) {
		if (serverDirectives.get(header)!=null) {
			serverDirectives.get(header).accept(full);
		}
		serverSubDirectories.forEach((b, n) -> {
			if (b.getAsBoolean()) {
				if (n.get(header)!=null) {
					n.get(header).accept(full);
				}
			}
		});
	}
}
