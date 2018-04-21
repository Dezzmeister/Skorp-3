package com.dezzy.skorp3.skorp3D.raycast2.core;

public class RaycastMap {
	public Wall[] walls;
	public final int WIDTH;
	public final int HEIGHT;
	
	public RaycastMap(int _width, int _height, Wall ... _walls) {
		walls = _walls;
		WIDTH = _width;
		HEIGHT = _height;
	}
	
	public int wallCount() {
		return walls.length;
	}
}
