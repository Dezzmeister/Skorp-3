package com.dezzy.skorp3.geometry3D;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

public class Line {
	public Vertex v1;
	public Vertex v2;
	public double length;
	public Shape3D shape = Shape3D.LINE;
	
	public Line(Vertex _v1, Vertex _v2) {
		v1 = _v1;
		v2 = _v2;
	}
}
