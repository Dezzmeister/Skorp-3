package com.dezzy.skorp3.skorp3D.raycast.core;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.log.Logger;

public class ElementTable {
	private List<Element> elements = new ArrayList<Element>();
	
	public void add(Element element) {
		for (Element e : elements) {
			if (element.id() == e.id() || element.name().equals(e.name())) {
				Logger.warn("Attempted to add duplicate Element to an ElementTable!");
				return;
			}
		}
		elements.add(element);
	}
	
	public Element getByID(int id) {
		for (Element e : elements) {
			if (e.id() == id) {
				return e;
			}
		}
		return Element.NONE;
	}
	
	public Element getByName(String name) {
		for (Element e : elements) {
			if (e.name().equals(name)) {
				return e;
			}
		}
		return Element.NONE;
	}
}
