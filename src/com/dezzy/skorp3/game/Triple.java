package com.dezzy.skorp3.game;

/**
 * For 3D business.
 * 
 * @author Dezzmeister
 *
 * @param <T> type parameter
 */
public class Triple<T> extends Pair<T> {
	
	public T z;
	
	public Triple(T _x, T _y, T _z) {
		super(_x, _y);
		z = _z;
	}

	@Override
	public String encode() {
		return super.encode() + " z:"+z;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
