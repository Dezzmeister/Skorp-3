package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Pair;
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
	public Pair point;
	public int width;
	public int height;
	
	//An extra coordinate pair, to be used where necessary
	public Pair endpoint;
	
	protected Entity() {
		
	}
	
	public Entity(int x, int y, int width, int height) {
		initGeometry(x,y,width,height);
	}
	
	public Entity(int x, int y, int width, int height, Shape _shape) {
		initGeometry(x,y,width,height);
		setShape(_shape);
	}
	
	private void initGeometry(int _x, int _y, int _width, int _height) {
		point = new Pair(_x,_y);
		width = _width;
		height = _height;
	}
	
	public void resize(int _width, int _height) {
		width = _width;
		height = _height;
	}
	
	public void placeAt(int _x, int _y) {
		point.x = _x;
		point.y = _y;
	}
	
	public void placeEndAt(int x, int y) {
		endpoint.x = x;
		endpoint.y = y;
	}
	
	public void placeAt(int x, int y, int x2, int y2) {
		placeAt(x,y);
		placeEndAt(x2,y2);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape _shape) {
		shape = _shape;
	}
}
