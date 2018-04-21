package com.dezzy.skorp3.skorp3D.raycast.core;

public class Vector {
	public double x;
	public double y;
	public double length = -1;
	
	public Vector(double _x, double _y) {
		x = _x;
		y = _y;
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
	
	public static double distance(Vector _v0, Vector _v1) {
		return Math.sqrt(((_v0.x - _v1.x) * (_v0.x - _v1.x)) + ((_v0.y - _v1.y) * (_v0.y - _v1.y)));
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
