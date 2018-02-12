package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Shape;

public class Player extends Entity {

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		shape = Shape.RECTANGLE;
	}
}
