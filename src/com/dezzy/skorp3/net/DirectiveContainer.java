package com.dezzy.skorp3.net;

import java.util.HashMap;
import java.util.Map;

public class DirectiveContainer {
	//Might not need a StringActor, Consumer<String> MAY work
	private volatile Map<String,StringActor<? extends Object>> clientDirectives = new HashMap<String,StringActor<? extends Object>>();
	private volatile Map<String,StringActor<? extends Object>> serverDirectives = new HashMap<String,StringActor<? extends Object>>();
	
	public <T> void addClientDirective(String header, StringActor<T> action) {
		clientDirectives.put(header,action);
	}
	
	public <T> void addServerDirective(String header, StringActor<T> action) {
		serverDirectives.put(header, action);
	}
	
	public Object executeClientDirective(String header, String full) {
		try {
			return clientDirectives.get(header).act(full);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Object executeServerDirective(String header, String full) {
		try {
			return serverDirectives.get(header).act(full);
		} catch (Exception e) {
			return null;
		}
	}
}
