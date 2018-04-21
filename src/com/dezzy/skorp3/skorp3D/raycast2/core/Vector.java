package com.dezzy.skorp3.skorp3D.raycast2.core;

public class Vector {
	public double x;
	public double y;
	
	public Vector(double _x, double _y) {
		x = _x;
		y = _y;
	}
	
	public static double distance(Vector _v0, Vector _v1) {
		return Math.sqrt(((_v0.x - _v1.x) * (_v0.x - _v1.x)) + ((_v0.y - _v1.y) * (_v0.y - _v1.y)));
	}
}
