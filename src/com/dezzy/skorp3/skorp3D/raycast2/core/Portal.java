package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public class Portal implements Linetype {
	public Vector2 v0;
	public Vector2 v1;
	public Sector border1;
	public Sector border2;
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
	
	public Portal setBorders(Sector _border1, Sector _border2) {
		border1 = _border1;
		border2 = _border2;
		return this;
	}
	
	public void updateLength() {
		length = Vector2.distance(v0, v1);
	}
	
	/**
	 * Returns the <code>Sector</code> bordering this <code>Portal</code> that is NOT the <code>Sector</code>
	 * supplied. Defaults to <code>border2</code>.
	 * 
	 * @param not One of two border sectors of this <code>Portal</code>
	 * @return whichever border Sector is not an alias of "not"
	 */
	public Sector otherSector(Sector not) {
		return (not==border1) ? border2 : border1;
	}
	
	/**
	 * Returns the two sectors that border this <code>Portal</code>. Only two sectors can border a Portal, so
	 * the array returned is guaranteed to have a length of 2.
	 * 
	 * @return border sectors of this <code>Portal</code>
	 */
	public Sector[] borders() {
		return new Sector[] {border1,border2};
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
