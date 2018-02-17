package com.dezzy.skorp3.game;

/**
 * Pair's purpose is to allow for a Pair parameter where there would normally be and x and y value.
 * This doesn't mean you shouldn't write (x,y) functions, just make sure to also write (Pair) functions.
 * 
 * @author Dezzmeister
 *
 */
public final class Pair<T> {
	public T x;
	public T y;
	
	public Pair(T _x, T _y) {
		x = _x;
		y = _y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
