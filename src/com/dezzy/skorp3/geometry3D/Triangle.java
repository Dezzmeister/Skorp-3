package com.dezzy.skorp3.geometry3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

public class Triangle extends Entity3D {
	public Color color;
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;

	{
		shape = Shape3D.TRIANGLE;
	}
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3, Color _color) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
		color = _color;
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Triangle> decompose() {
		List<Triangle> result = new ArrayList<Triangle>();
		result.add(this);
		return result;
	}
}
