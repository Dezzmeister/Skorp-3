package com.dezzy.skorp3.skorp3D.primitive;

import java.awt.Color;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.geometry.Entity;

public class Quad extends Entity {
	private Triangle t1;
	private Triangle t2;
	
	{
		triangleCount = 2;
	}
	
	public Quad(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Color texture) {
		super(texture);
		t1 = new Triangle(v1,v2,v3,texture);
		t2 = new Triangle(v3,v4,v1,texture);
	}
	
	@Override
	public Triangle[] getTriangles() {
		return new Triangle[] {t1,t2};
	}

	@Override
	public void forceTransform(Matrix4 custom) {
		matrixTransform(custom);
	}

	@Override
	protected void transform() {
		Matrix4 u1 = getTransformationMatrix();
		matrixTransform(u1);
	}
	
	private void matrixTransform(Matrix4 u1) {
		t1.v1 = u1.transform(t1.v1);
		t1.v2 = u1.transform(t1.v2);
		t1.v3 = u1.transform(t1.v3);
		
		t2.v1 = u1.transform(t2.v1);
		t2.v2 = u1.transform(t2.v2);
		t2.v3 = u1.transform(t2.v3);
	}
	
}
