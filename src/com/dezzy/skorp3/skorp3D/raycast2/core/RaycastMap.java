package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Color;

public class RaycastMap {
	/**
	 * Used by <code>preShade()</code> to determine what axis to calculate angles off of.
	 */
	public static final Wall AXIS = new Wall(0,0,1,0,Color.BLACK);
	/**
	 * The difference in darkness between an unshaded wall and a wall with maximum possible shading.
	 */
	private static final int SHADE_RANGE = 20;
	public Wall[] walls;
	public Sector[] sectors;
	public final int WIDTH;
	public final int HEIGHT;
	
	public RaycastMap(int _width, int _height, Wall ... _walls) {
		walls = _walls;
		WIDTH = _width;
		HEIGHT = _height;
		preShade();
	}
	
	public RaycastMap(int _width, int _height, Sector ... _sectors) {
		sectors = _sectors;
		WIDTH = _width;
		HEIGHT = _height;
		sectors = _sectors;
		preShade();
	}
	
	public int wallCount() {
		return walls.length;
	}
	
	/**
	 * Darkens the color of each wall based on its angle from the x axis.
	 */
	public void preShade() {
		/*
		for (int i = 0; i < walls.length; i++) {
			double angle = Math.toDegrees(Wall.angleBetweenLines(walls[i], AXIS));
			angle %= 180;
			double norm = angle/180;
			int shadeValue = (int)(norm*(SHADE_RANGE));
			
			walls[i].shade(shadeValue);			
		}
		*/
	}
}
