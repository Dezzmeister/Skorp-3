package com.dezzy.skorp3.skorp3D.primitive;

import com.dezzy.skorp3.game3D.Updateable;

/**
 * Superclass for com.dezzy.skorp3.skorp3D.geometry.Entity. Represents an object that can
 * be transformed in 3D space.
 * 
 * @author Dezzmeister
 *
 */
public abstract class Transformable implements Updateable {
	
	/**
	 * Rotates an object around the x-axis.
	 * 
	 * @param deg angle to rotate, in degrees
	 */
	public abstract void rotateX(double deg);
	
	/**
	 * Rotates an object around the y-axis.
	 * 
	 * @param deg angle to rotate, in degrees
	 */
	public abstract void rotateY(double deg);
	
	/**
	 * Rotates an object around the z-axis.
	 * 
	 * @param deg angle to rotate, in degrees
	 */
	public abstract void rotateZ(double deg);
	
	/**
	 * Translates an object in 3D space.
	 * 
	 * @param x distance to translate object in x direction
	 * @param y distance to translate object in y direction
	 * @param z distance to translate object in z direction
	 */
	public abstract void translate(double x, double y, double z);
	
	/**
	 * Scales an object in 3D space.
	 * 
	 * @param x factor to scale object in x direction
	 * @param y factor to scale object in y direction
	 * @param z factor to scale object in z direction
	 */
	public abstract void scale(double x, double y, double z);
	
	/**
	 * Applies transformations specified using transformation methods.
	 * Automatically calls update() and a protected method transform(), which should
	 * be defined to update the shape accordingly.
	 */
	public void apply() {
		transform();
		update();
	}
	
	/**
	 * Uses transformations specified through transformation methods and applies them. Differs
	 * from apply() by not calling update(). Also not intended for direct use.
	 */
	protected abstract void transform();
}
