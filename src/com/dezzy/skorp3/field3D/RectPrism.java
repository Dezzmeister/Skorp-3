package com.dezzy.skorp3.field3D;

import java.util.List;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

public class RectPrism extends Geometric3D {
	
	{
		shape = Shape3D.RECTPRISM;
	}
	public RectPrism(double x, double y, double z) {
		super(x, y, z);
		
	}
	
	public RectPrism(double x, double y, double z, int _width, int _height, int _length) {
		super(x,y,z);
		width = _width;
		height = _height;
		length = _length;
	}

	@Override
	public List<Vertex> decompose() {
		// TODO Auto-generated method stub
		return null;
	}
}
