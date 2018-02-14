package com.dezzy.skorp3.game;

/**
 * Pair's purpose is to allow for a Pair parameter where there would normally be and x and y value.
 * This doesn't mean you shouldn't write (x,y) functions, just make sure to also write (Pair) functions.
 * 
 * @author Dezzmeister
 *
 */
public final class Pair {
	public int x;
	public int y;
	
	public Pair(int _x, int _y) {
		x = _x;
		y = _y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
