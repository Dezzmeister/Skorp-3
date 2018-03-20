package com.dezzy.skorp3.geometry3D;

import java.awt.Color;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

public class Triangle {
	public Color color;
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;
	private Shape3D shape = Shape3D.TRIANGLE;
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
	}
	
	public Shape3D getShape() {
		return shape;
	}
}
