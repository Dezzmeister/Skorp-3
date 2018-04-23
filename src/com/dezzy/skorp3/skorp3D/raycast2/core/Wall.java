package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Color;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class Wall {
	public static final Texture2 defaultTexture = new Texture2("assets/raycast/textures/darkbricks.png",512,512);
	
	public Vector v0;
	public Vector v1;
	public double length;
	public int color = 0x00FFFFFF;
	public int shadeValue = 0;
	public Texture2 texture = defaultTexture;
	public int xTiles = 1;
	public int yTiles = 1;
	
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
	
	public Wall setTexture(Texture2 _texture) {
		texture = _texture;
		return this;
	}
	
	public double getNorm(Vector v) {
		return Vector.distance(v, v0)/length;
	}
	
	public Texture2 getTexture() {
		return texture;
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
	
	public static int getIntFromRGB(Color color) {
		int rgb = color.getRed();
		rgb = (rgb << 8) + color.getGreen();
		rgb = (rgb << 8) + color.getBlue();
		
		return rgb;
	}
	
	public static Color getRGBFromInt(int col) {
		int red = (col >> 16) & 0xFF;
		int green = (col >> 8) & 0xFF;
		int blue = col & 0xFF;
		
		return new Color(red,green,blue);
	}
	
	public Wall shade(int darkenBy) {
		texture = new Texture2(texture.path,texture.width,texture.height);
		for (int i = 0; i < texture.pixels.length; i++) {
			if (texture.pixels[i] != Texture2.ALPHA) {
				Color c = new Color(texture.pixels[i]);
				int red = c.getRed()-darkenBy;
				int green = c.getGreen()-darkenBy;
				int blue = c.getBlue()-darkenBy;
				Color newcolor = new Color(red >= 0 ? red : 0, green >= 0 ? green : 0, blue >= 0 ? blue : 0);
				texture.pixels[i] = getIntFromRGB(newcolor);
			}
		}
		return this;
	}
	
	public Wall tile(int _xTiles, int _yTiles) {
		xTiles = _xTiles;
		yTiles = _yTiles;
		return this;
	}
}
