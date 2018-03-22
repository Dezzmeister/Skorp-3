package com.dezzy.skorp3.field3D;

import com.dezzy.skorp3.math3D.Vertex;

public abstract class Geometric3D extends Entity3D {
	public int width;	//X axis
	public int height;	//Y axis
	public int length;	//Z axis
	
	public Geometric3D(double x, double y, double z)	{
		point = new Vertex(x,y,z);
	}
	
	public Geometric3D(double x, double y, double z, int _width, int _height, int _length) {
		point = new Vertex(x,y,z);
		width = _width;
		height = _height;
		length = _length;
	}
	
	public abstract String encode();
}
