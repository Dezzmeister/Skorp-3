package com.dezzy.skorp3.math3D;

import com.dezzy.skorp3.game.Triple;

public class Vertex extends Triple<Double> {
	public double w;
	
	public Vertex(double _x, double _y, double _z) {
		super(_x, _y, _z);
	}
	
	public Vertex(double _x, double _y, double _z, double _w) {
		super(_x, _y, _z);
		w = _w;
	}
	
	public static double distance(Vertex v1, Vertex v2) {
		return Math.sqrt(((v1.x-v2.x)*(v1.x-v2.x))+
					     ((v1.y-v2.y)*(v1.y-v2.y))+
					     ((v1.z-v2.z)*(v1.z-v2.z)));
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
}
