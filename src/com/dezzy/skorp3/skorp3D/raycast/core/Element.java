package com.dezzy.skorp3.skorp3D.raycast.core;

import java.awt.Color;

import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.log.Logger;
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
public class Element implements Cloneable {
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
	
	private boolean thin = false;
	
	public Orientation orientation = Orientation.NOT_THIN;
	
	public Line segment;
	
	/**
	 * Both textures must be the same size.
	 */
	private Texture frontTexture = new Texture("assets/raycast/textures/wall.png",16);
	private Texture sideTexture = frontTexture.darken();
	
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
	
	/**
	 * Sets the front texture of the Element and sets the side texture to a 
	 * darkened version of the supplied texture. If setting a custom side texture,
	 * call this first.
	 * 
	 * @param _texture Texture to be set to front texture
	 * @return this Element
	 */
	public Element setFrontTexture(Texture _texture) {
		frontTexture = _texture;
		sideTexture = frontTexture.darken();
		return this;
	}
	
	/**
	 * Sets the side texture of the Element. Defaults to a darkened version
	 * of the front texture.
	 * <p>
	 * ALWAYS CALL AFTER <code>setFrontTexture()</code>
	 * 
	 * @param _texture Texture to be set to side texture
	 * @return this Element
	 */
	public Element setSideTexture(Texture _texture) {
		if (_texture.SIZE == frontTexture.SIZE) {
			sideTexture = _texture;
		} else {
			sideTexture = frontTexture.darken();
		}
		return this;
	}
	
	public Element applyTexture(Texture _texture) {
		frontTexture = _texture;
		sideTexture = frontTexture.darken();
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
	
	public Element makeThin(Orientation facing, Line _segment) {
		thin = true;
		if (facing == Orientation.NOT_THIN) {
			System.out.println("Cannot make an Element thin and pass Orientation.NOT_THIN! Defaulting to Orientation.XPLANE.");
			Logger.warn("Cannot make an Element thin and pass in Orientation.NOT_THIN! Defaulting to Orientation.XPLANE.");
			orientation = Orientation.XPLANE;
		} else {
			orientation = facing;
		}
		segment = _segment;
		return this;
	}
	
	public boolean isThin() {
		return thin;
	}
	
	@Override
	public Element clone() {
		Element element = new Element(id, name, color, changeable);
		element.setFrontTexture(frontTexture);
		element.setSideTexture(sideTexture);
		element.makeThin(orientation,segment);
		
		return element;
	}
}
