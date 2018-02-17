package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Shape;

/**
 * Geometric should be used for anything that requires more than a Pair and a Shape. Previously, Entity contained everything in Geometric, until Point was added and it was
 * realized that if Point extended Entity (which it had to), 1 Point would have much more information than it needed (such as a width, height, and endpoint).
 * 
 * @author Dezzmeister
 *
 */
public abstract class Geometric extends Entity {
	
	public int width;
	public int height;
	
	public Pair<Integer> endpoint;
	
	{
		point = new Pair<Integer>(0,0);
		endpoint = new Pair<Integer>(0,0);
	}
	
	public Geometric() {
		// TODO Auto-generated constructor stub
	}

	public Geometric(int x, int y, int width, int height) {
		initGeometry(x,y,width,height);
	}
	
	public Geometric(int x, int y, int width, int height, Shape _shape) {
		initGeometry(x,y,width,height);
		shape = _shape;
	}
	
	public Geometric(Shape _shape) {
		super(_shape);
	}

	private void initGeometry(int _x, int _y, int _width, int _height) {
		point = new Pair<Integer>(_x,_y);
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
}
