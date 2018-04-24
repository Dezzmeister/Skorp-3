package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;

public class Sector {
	public Vector[] points;
	public Wall[] walls;
	
	public Sector(Vector ... _points) {
		points = _points;
	}
	
	public Sector defineWalls(Wall ... _walls) {
		walls = _walls;
		return this;
	}
}
