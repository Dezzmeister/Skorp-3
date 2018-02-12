package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Shape;

/**
 * Entity should be a superclass for any game component that appears on the field. Entities can collide with each other
 * by means of Shape.
 * 
 * @see Shape
 * @author Dezzmeister
 *
 */
public abstract class Entity {
	protected Shape shape;
	public int x; //Center
	public int y; //Center
	public int width;
	public int height;
	
	public Entity(int x, int y, int width, int height) {
		initGeometry(x,y,width,height);
	}
	
	public Entity(int x, int y, int width, int height, Shape _shape) {
		initGeometry(x,y,width,height);
		setShape(_shape);
	}
	
	private void initGeometry(int _x, int _y, int _width, int _height) {
		x = _x;
		y = _y;
		width = _width;
		height = _height;
	}
	
	public void resize(int _width, int _height) {
		width = _width;
		height = _height;
	}
	
	public void placeAt(int _x, int _y) {
		x = _x;
		y = _y;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape _shape) {
		shape = _shape;
	}
}
