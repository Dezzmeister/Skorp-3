package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class Sector {
	public static final Texture2 DEFAULT_FLOORTEXTURE = new Texture2("assets/raycast/textures/floortiles.png",512,512);
	public static final Texture2 DEFAULT_CEILTEXTURE = new Texture2("assets/raycast/textures/plaster.png",512,512);
	
	public Vector2[] points;
	public Wall[] walls;
	
	public Texture2 ceiltexture = DEFAULT_CEILTEXTURE;
	public Texture2 floortexture = DEFAULT_FLOORTEXTURE;
	
	public float ceilXTiles = 1;
	public float ceilYTiles = 1;
	public float floorXTiles = 1;
	public float floorYTiles = 1;
	
	public float floorHeight = 0;
	public float ceilHeight = 1.0f;
	
	private final AtomicBoolean rendered = new AtomicBoolean(false);
	
	/**
	 * Floor texture uv coordinates. 
	 */
	public Vector2 uvf0;
	public Vector2 uvf1;
	
	/**
	 * Ceiling texture uv coordinates.
	 */
	public Vector2 uvc0;
	public Vector2 uvc1;
	
	public Sector() {
		
	}
	
	public Sector(Vector2 ... _points) {
		points = _points;
		walls = new Wall[0];
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
	
	public Sector setFloor(float height) {
		floorHeight = height;
		return this;
	}
	
	public Sector setCeiling(float height) {
		ceilHeight = height;
		return this;
	}
	
	public Sector mapFloor(Vector2 _uvf0, Vector2 _uvf1) {
		uvf0 = _uvf0;
		uvf1 = _uvf1;
		return this;
	}
	
	public Sector mapCeiling(Vector2 _uvc0, Vector2 _uvc1) {
		uvc0 = _uvc0;
		uvc1 = _uvc1;
		return this;
	}
	
	public synchronized void markAsRendered() {
		rendered.set(true);
	}
	
	public void markAsUnrendered() {
		rendered.set(false);
	}
	
	public synchronized boolean hasBeenRendered() {
		return rendered.get();
	}
}
