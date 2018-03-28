package com.dezzy.skorp3.field3D;

import com.dezzy.skorp3.math3D.Vertex;

public abstract class Geometric3D extends Entity3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6207377303394476708L;
	protected int width;	//X axis
	protected int height;	//Y axis
	protected int length;	//Z axis
	
	public Geometric3D(double x, double y, double z)	{
		point = new Vertex(x,y,z);
	}
	
	public Geometric3D(double x, double y, double z, int _width, int _height, int _length) {
		point = new Vertex(x,y,z);
		width = _width;
		height = _height;
		length = _length;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public int length() {
		return length;
	}
	
	public void width(int _width) {
		width = _width;
		update();
	}
	
	public void height(int _height) {
		height = _height;
		update();
	}
	
	public void length(int _length) {
		length = _length;
		update();
	}
	
	public abstract String encode();
}
