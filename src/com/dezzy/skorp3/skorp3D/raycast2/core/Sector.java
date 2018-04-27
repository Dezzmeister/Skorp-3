package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class Sector {
	public Vector[] points;
	public Wall[] walls;
	
	public Texture2 ceiltexture = new Texture2("assets/raycast/textures/plaster.png",512,512);
	public Texture2 floortexture = new Texture2("assets/raycast/textures/floortiles.png",512,512);
	
	public double ceilXTiles = 1;
	public double ceilYTiles = 1;
	public double floorXTiles = 1;
	public double floorYTiles = 1;
	
	public double floorScale = 1;
	public double ceilScale = 1;
	
	public Sector(Vector ... _points) {
		points = _points;
	}
	
	public Sector defineWalls(Wall ... _walls) {
		walls = _walls;
		return this;
	}
	
	public Sector tileFloor(double _xTiles, double _yTiles) {
		floorXTiles = _xTiles;
		floorYTiles = _yTiles;
		return this;
	}
	
	public Sector tileCeiling(double _xTiles, double _yTiles) {
		ceilXTiles = _xTiles;
		ceilYTiles = _yTiles;
		return this;
	}
	
	public Sector setFloor(double scale) {
		floorScale = scale;
		return this;
	}
	
	public Sector setCeiling(double scale) {
		ceilScale = scale;
		return this;
	}
}
