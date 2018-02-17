package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Shape;

public class Player extends Geometric {

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height, Shape.RECTANGLE);
	}
}