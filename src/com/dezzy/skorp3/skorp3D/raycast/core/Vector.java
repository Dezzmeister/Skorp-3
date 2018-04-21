package com.dezzy.skorp3.skorp3D.raycast.core;

public class Vector {
	public double x;
	public double y;
	public double length = -1;
	
	public Vector(double _x, double _y) {
		x = _x;
		y = _y;
		updateLength();
	}
	
	public Vector add(Vector v) {
		return new Vector(v.x + x, v.y + y);
	}
	
	public void updateLength() {
		length = Math.sqrt((x * x) + (y * y));
	}
	
	public Vector subtract(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
