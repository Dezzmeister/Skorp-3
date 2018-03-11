package com.dezzy.skorp3.net;

import java.util.HashMap;

public class NamedMap<T, E> extends HashMap<T,E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public NamedMap(String _name) {
		super();
		name = _name;
	}
	
	public String name() {
		return name;
	}
}
