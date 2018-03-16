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
}
