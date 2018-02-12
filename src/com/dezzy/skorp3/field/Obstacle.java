package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Shape;

public class Obstacle extends Entity {

	public Obstacle(int x, int y, int width, int height) {
		super(x, y, width, height);
		shape = Shape.RECTANGLE;
	}

	public Obstacle(int x, int y, int width, int height, Shape _shape) {
		super(x, y, width, height, _shape);
		
	}

}
