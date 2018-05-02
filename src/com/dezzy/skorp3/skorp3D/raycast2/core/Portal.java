package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public class Portal implements Linetype {
	public Vector2 v0;
	public Vector2 v1;
	public float length;
	
	public Portal(Vector2 _v0, Vector2 _v1) {
		v0 = _v0;
		v1 = _v1;
		
		updateLength();
	}
	
	public Portal(float x1, float y1, float x2, float y2) {
		v0 = new Vector2(x1,y1);
		v1 = new Vector2(x2,y2);
		
		updateLength();
	}
	
	public void updateLength() {
		length = Vector2.distance(v0, v1);
	}

	@Override
	public Vector2 v0() {
		return v0;
	}

	@Override
	public Vector2 v1() {
		return v1;
	}

	@Override
	public float length() {
		return length;
	}
}
