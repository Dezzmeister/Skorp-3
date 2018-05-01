package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class Sector {
	public Vector2[] points;
	public Wall[] walls;
	
	public Texture2 ceiltexture = new Texture2("assets/raycast/textures/plaster.png",512,512);
	public Texture2 floortexture = new Texture2("assets/raycast/textures/floortiles.png",512,512);
	
	public float ceilXTiles = 1;
	public float ceilYTiles = 1;
	public float floorXTiles = 1;
	public float floorYTiles = 1;
	
	public float floorScale = 1;
	public float ceilScale = 1;
	
	public Sector(Vector2 ... _points) {
		points = _points;
	}
	
	public Sector defineWalls(Wall ... _walls) {
		walls = _walls;
		return this;
	}
	
	public Sector tileFloor(float _xTiles, float _yTiles) {
		floorXTiles = _xTiles;
		floorYTiles = _yTiles;
		return this;
	}
	
	public Sector tileCeiling(float _xTiles, float _yTiles) {
		ceilXTiles = _xTiles;
		ceilYTiles = _yTiles;
		return this;
	}
	
	public Sector setFloor(float scale) {
		floorScale = scale;
		return this;
	}
	
	public Sector setCeiling(float scale) {
		ceilScale = scale;
		return this;
	}
}
