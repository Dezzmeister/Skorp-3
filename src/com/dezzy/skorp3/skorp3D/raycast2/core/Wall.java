package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import java.awt.Color;

public class Wall {
	public Vector v0;
	public Vector v1;
	public double length;
	public int color = 0x00FFFFFF;
	
	public Wall(Vector _v0, Vector _v1, Color _color) {
		v0 = _v0;
		v1 = _v1;
		color = getIntFromRGB(_color);
		updateLength();
	}
	
	public Wall(double x1, double y1, double x2, double y2, Color _color) {
		v0 = new Vector(x1,y1);
		v1 = new Vector(x2,y2);
		color = getIntFromRGB(_color);
		updateLength();
	}
	
	public Wall(double x1, double y1, double x2, double y2) {
		v0 = new Vector(x1,y1);
		v1 = new Vector(x2,y2);
		updateLength();
	}
	
	public Wall(Vector _v0, Vector _v1) {
		v0 = _v0;
		v1 = _v1;
	}
	
	private void updateLength() {
		length = Vector.distance(v0, v1);
	}
	
	public double slope() {
		double xDiff = v1.x-v0.x;
		double yDiff = v1.y-v0.y;
		return yDiff/xDiff;
	}
	
	public double yIntercept() {
		double x = v0.x;
		double y = v0.y;
		
		return y - (slope()*x);
	}
	
	public void setColor(Color _color) {
		color = getIntFromRGB(_color);
	}
	
	public Color getColor() {
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
		
		return new Color(red,green,blue);
	}
	
	/**
	 * Returns the angle between two Walls, in radians.
	 * 
	 * @param wall1 first wall
	 * @param wall2 second wall
	 * @return angle in radians
	 */
	public static double angleBetweenLines(Wall wall1, Wall wall2) {
		double angle1 = Math.atan2(wall1.v0.y - wall1.v1.y, wall1.v0.x - wall1.v1.x);
		double angle2 = Math.atan2(wall2.v0.y - wall2.v1.y, wall2.v0.x - wall2.v1.x);
		  
		return Math.abs(angle1-angle2);
	}
	
	private int getIntFromRGB(Color color) {
		int rgb = color.getRed();
		rgb = (rgb << 8) + color.getGreen();
		rgb = (rgb << 8) + color.getBlue();
		
		return rgb;
	}
}
