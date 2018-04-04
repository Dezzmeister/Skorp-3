package com.dezzy.skorp3.field3D;

public interface Transformable3D {
	
	public void rotateX(double deg);
	
	public void rotateY(double deg);
	
	public void rotateZ(double deg);
	
	public void translate(double x, double y, double z);
	
	public void scale(double x, double y, double z);
}
