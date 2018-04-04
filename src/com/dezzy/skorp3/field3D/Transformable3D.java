package com.dezzy.skorp3.field3D;

import com.dezzy.skorp3.game3D.Updateable;

public interface Transformable3D extends Updateable {
	
	public void rotateX(double deg);
	
	public void rotateY(double deg);
	
	public void rotateZ(double deg);
	
	public void translate(double x, double y, double z);
	
	public void scale(double x, double y, double z);
	
	public default void apply() {
		update();
		applyTransformations();
	}
	
	public void applyTransformations();
}
