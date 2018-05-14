package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaycastMap {
	/**
	 * Used by <code>preShade()</code> to determine what axis to calculate angles off of.
	 */
	public static final Wall AXIS = new Wall(0,0,1,0,Color.BLACK);
	/**
	 * The difference in darkness between an unshaded wall and a wall with maximum possible shading.
	 */
	private static final int SHADE_RANGE = 20;
	public Sector[] sectors;
	public Portal[] portals;
	public final int WIDTH;
	public final int HEIGHT;
	
	public RaycastMap(int _width, int _height, Sector ... _sectors) {
		sectors = _sectors;
		WIDTH = _width;
		HEIGHT = _height;
		sectors = _sectors;
	}
	
	public RaycastMap definePortals(Portal ... _portals) {
		portals = _portals;
		addPortalsToWalls();
		return this;
	}
	
	/**
	 * Adds every Portal in the portal array to its border sectors' wall arrays. This allows the 
	 * raycaster to check Portals like conventional Walls so that it can determine starting and ending points
	 * for drawing other sectors.
	 */
	private void addPortalsToWalls() {
		for (Portal p : portals) {
			for (Sector s : p.borders()) {
				Wall[] walls = new Wall[s.walls.length+1];
				for (int i = 0; i < s.walls.length; i++) {
					walls[i] = s.walls[i];
				}
				walls[walls.length-1] = new Wall(p).makePortal();
				s.defineWalls(walls);
			}
		}
	}
}
