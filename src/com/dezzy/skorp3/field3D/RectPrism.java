package com.dezzy.skorp3.field3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Vertex;

public class RectPrism extends Geometric3D {
	
	{
		shape = Shape3D.RECTPRISM;
	}
	public RectPrism(double x, double y, double z, Color _color) {
		super(x, y, z);
		color = _color;
	}
	
	public RectPrism(double x, double y, double z, int _width, int _height, int _length, Color _color) {
		super(x,y,z);
		width = _width;
		height = _height;
		length = _length;
		color = _color;
	}

	@Override
	public List<Triangle> decompose() {
		List<Triangle> result = new ArrayList<Triangle>();
		double x = point.x;
		double y = point.y;
		double z = point.z;
		
		Triangle yee = new Triangle(new Vertex(x,y,z),
									new Vertex(x+50,y+50,z+50),
									new Vertex(x-100,y-100,z-100),
									color);
		
		result.add(yee);
		return result;
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}
}
