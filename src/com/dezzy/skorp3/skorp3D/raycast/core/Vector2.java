package com.dezzy.skorp3.skorp3D.raycast.core;

public class Vector2 {
	public float x;
	public float y;
	public float length = -1;
	
	public Vector2(float _x, float _y) {
		x = _x;
		y = _y;
	}
	
	public Vector2 add(Vector2 v) {
		return new Vector2(v.x + x, v.y + y);
	}
	
	public void updateLength() {
		length = (float)Math.sqrt((x * x) + (y * y));
	}
	
	public Vector2 subtract(Vector2 v) {
		return new Vector2(x - v.x, y - v.y);
	}
	
	public static float distance(Vector2 _v0, Vector2 _v1) {
		return (float) Math.sqrt(((_v0.x - _v1.x) * (_v0.x - _v1.x)) + ((_v0.y - _v1.y) * (_v0.y - _v1.y)));
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
