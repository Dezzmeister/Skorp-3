package com.dezzy.skorp3.skorp3D.primitive;

import java.awt.Color;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.geometry.Entity;

public class Triangle extends Entity {
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;
	
	{
		triangleCount = 1;
	}
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
	}
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3, Color _texture) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
		setTexture(_texture);
	}
	
	public Triangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, Color _texture) {
		v1 = new Vertex(x1,y1,z1);
		v2 = new Vertex(x2,y2,z2);
		v3 = new Vertex(x3,y3,z3);
		setTexture(_texture);
	}
	
	@Override
	public Triangle[] getTriangles() {
		return new Triangle[]{this};
	}

	@Override
	protected void transform() {
		Matrix4 u1 = getTransformationMatrix();
		v1 = u1.transform(v1);
		v2 = u1.transform(v2);
		v3 = u1.transform(v3);
	}
	
	@Override
	public String toString() {
		return v1.toString() + "\n" +
			   v2.toString() + "\n" +
			   v3.toString();
	}

	@Override
	public void forceTransform(Matrix4 custom) {
		v1 = custom.transform(v1);
		v2 = custom.transform(v2);
		v3 = custom.transform(v3);
		update();
	}
}
