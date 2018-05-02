package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public interface Linetype {
	
	/**
	 * Returns the first endpoint of the line.
	 * 
	 * @return an endpoint of the line
	 */
	Vector2 v0();
	
	/**
	 * Returns the second endpoint of the line.
	 * 
	 * @return and endpoint of the line
	 */
	Vector2 v1();
	
	float length();
	
	/**
	 * Takes a Vector that is assumed to lie on this Line and returns the normalized distance from
	 * the first endpoint to this Vector. For the purpose of speed, <code>getNorm()</code> does not perform
	 * any checks to ensure that the supplied Vector lies on this line. For this reason, it is possible to receive
	 * values greater than 1 if the supplied Vector is not on this line.
	 * 
	 * @param v Vector, should be known to lie on line
	 * @return normalized distance from first endpoint to v
	 */
	default float getNorm(Vector2 v) {
		return Vector2.distance(v, v0())/length();
	} 
}
