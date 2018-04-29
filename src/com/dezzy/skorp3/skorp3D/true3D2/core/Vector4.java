package com.dezzy.skorp3.skorp3D.true3D2.core;

public class Vector4 {
	public double x;
	public double y;
	public double z;
	public double w;
	
	public Vector4(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	
	public Vector4(double _x, double _y, double _z, double _w) {
		x = _x;
		y = _y;
		z = _z;
		w = -w;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}
}
