package com.dezzy.skorp3.skorp3D.raycast.core;

import java.awt.Color;

import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

/**
 * Represents an element of a world map. In the game, this is seen as a colored block.
 * <p>
 * Elements have an ID, a name, and a Color.
 * <p>
 * IDs -1 and 0 and names <code>"null"</code> and <code>"space"</code> are reserved.
 * 
 * @author Dezzmeister
 *
 */
//TODO: Add textures
@Urgency(4)
public class Element {
	/**
	 * Represents a null or error element. Visible in game as a pink box.
	 */
	public static final Element NONE = new Element(-1,"null",Color.PINK, false);
	/**
	 * Represents an empty space.
	 */
	public static final Element SPACE = new Element(0,"space",Color.PINK,true);
	
	private int id = 0;
	
	private String name;
	
	private Color color;
	
	private boolean changeable = false;
	
	//private Texture frontTexture = new Texture("assets/raycast/textures/wall.png",64);
	//private Texture sideTexture = frontTexture.darken();
	
	private Texture frontTexture = new Texture("assets/raycast/textures/front.png",64);
	private Texture sideTexture = new Texture("assets/raycast/textures/side.png",64);
	
	public Element(int _id, String _name, Color _color) {
		id = _id;
		name = _name;
		color = _color;
	}
	
	public Element(int _id, String _name, Color _color, boolean _changeable) {
		id = _id;
		name = _name;
		color = _color;
		changeable = _changeable;
	}
	
	public Element setFrontTexture(Texture _texture) {
		frontTexture = _texture;
		return this;
	}
	
	public Element setSideTexture(Texture _texture) {
		sideTexture = _texture;
		return this;
	}
	
	/**
	 * Tries to change this Element into the specified
	 * Element. Returns true if the change is successful.
	 * 
	 * @param element Element to change into
	 * @return true if change is successful
	 */
	public boolean tryChange(Element element) {
		if (changeable) {
			id = element.id();
			name = element.name();
			color = element.color();
			changeable = element.isChangeable();
			return true;
		}
		return false;
	}
	
	public boolean isChangeable() {
		return changeable;
	}
	
	public int id() {
		return id;
	}
	
	public String name() {
		return name;
	}
	
	public Color color() {
		return color;
	}
	
	public Texture frontTexture() {
		return frontTexture;
	}
	
	public Texture sideTexture() {
		return sideTexture;
	}
	
	public void color(Color _color) {
		color = _color;
	}
}
