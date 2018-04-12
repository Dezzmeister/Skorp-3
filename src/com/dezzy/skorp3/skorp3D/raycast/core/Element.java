package com.dezzy.skorp3.skorp3D.raycast.core;

import java.awt.Color;

public class Element {
	public static final Element NONE = new Element(-1,"null",Color.BLACK);
	
	private int id = 0;
	
	private String name;
	
	public Color color;
	
	public Element(int _id, String _name, Color _color) {
		id = _id;
		name = _name;
	}
	
	public int id() {
		return id;
	}
	
	public String name() {
		return name;
	}
}
