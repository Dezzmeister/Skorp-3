package com.dezzy.skorp3.net;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPManager will control a Client-Server TCP connection using "directives."
 * Directives are just blocks of code that will run on either the Client or the Server when an associated header is received.
 * Directive actions will have one parameter, a String containing the received message (without the header). The code provided as an action
 * will act on the String appropriately, and may return something if need be. 
 * 
 * @author Dezzmeister
 * @see StringActor
 *
 */
public class TCPManager {
	private Map<String,StringActor<?>> clientDirectives = new HashMap<String,StringActor<?>>();
	private Map<String,StringActor<?>> serverDirectives = new HashMap<String,StringActor<?>>();
	
	public TCPManager() {
		// TODO Auto-generated constructor stub
	}
	
	public <T> void addClientDirective(String header, StringActor<T> action) {
		clientDirectives.put(header,action);
	}
	
	public <T> void addServerDirective(String header, StringActor<T> action) {
		serverDirectives.put(header, action);
	}
	
	public Object executeClientDirective(String header) {
		return clientDirectives.get(header).act(header);
	}
	
	public Object executeServerDirective(String header) {
		return serverDirectives.get(header).act(header);
	}
}
