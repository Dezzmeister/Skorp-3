package com.dezzy.skorp3.geometry3D;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

public class Line {
	private Vertex v1;
	private Vertex v2;
	private double length;
	private final Shape3D shape = Shape3D.LINE;
	
	public Line(Vertex _v1, Vertex _v2) {
		v1 = _v1;
		v2 = _v2;
	}
	
	public Shape3D getShape() {
		return shape;
	}
	
	public double length() {
		return length;
	}
	
	public void v1(Vertex _v1) {
		v1 = _v1;
		computeLength();
	}
	
	public void v2(Vertex _v2) {
		v2 = _v2;
		computeLength();
	}
	
	public Vertex v1() {
		return v1;
	}
	
	public Vertex v2() {
		return v2;
	}
	
	private void computeLength() {
		length = Vertex.distance(v1, v2);
	}
}
