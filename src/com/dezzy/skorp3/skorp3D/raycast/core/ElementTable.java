package com.dezzy.skorp3.skorp3D.raycast.core;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.log.Logger;

/**
 * A table of Elements with unique IDs and names. Used when defining WorldMaps.
 * <p>
 * If defining a WorldMap with a 2D array of ints or names, and ElementTable is used to map
 * the names to specific elements. 
 * 
 * @author Dezzmeister
 *
 */
public class ElementTable {
	private List<Element> elements = new ArrayList<Element>();
	
	{
		add(Element.NONE);
		add(Element.SPACE);
	}
	
	/**
	 * Adds a unique Element to the ElementTable. Ensures that an element with existing ID and name is not already present.
	 * 
	 * @param element Element to be added
	 */
	public void add(Element element) {
		for (Element e : elements) {
			if (element.id() == e.id() || element.name().equals(e.name())) {
				Logger.warn("Attempted to add duplicate Element to an ElementTable!");
				return;
			}
		}
		elements.add(element);
	}
	
	/**
	 * Returns an Element with the specified ID. If an Element with this ID is
	 * not found, returns Element.NONE.
	 * 
	 * @param id int ID of the Element
	 * @return an Element with this ID or ID -1
	 */
	public Element getByID(int id) {
		for (Element e : elements) {
			if (e.id() == id) {
				return e;
			}
		}
		return Element.NONE;
	}
	
	/**
	 * Returns an Element with the specified name. If an Element with this name
	 * is not found, returns Element.NONE.
	 * 
	 * @param name String name of the Element
	 * @return and Element with this name or name "null"
	 */
	public Element getByName(String name) {
		for (Element e : elements) {
			if (e.name().equals(name)) {
				return e;
			}
		}
		return Element.NONE;
	}
}
