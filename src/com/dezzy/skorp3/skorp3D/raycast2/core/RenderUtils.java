package com.dezzy.skorp3.skorp3D.raycast2.core;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public final class RenderUtils {
	
	private RenderUtils() {
		throw new RuntimeException();
	}
	
	public static Vector2 rayHitSegment(Vector2 rayStart, Vector2 rayDirection, Wall segment) {
		Vector2 r0 = rayStart;
		Vector2 r1 = rayDirection;
		Vector2 a = segment.v0;
		Vector2 b = segment.v1;
		
		Vector2 s1,s2;
		s1 = new Vector2(r1.x-r0.x,r1.y-r0.y);
		s2 = new Vector2(b.x-a.x,b.y-a.y);
		  
		float s,t;
		s = (-s1.y * (r0.x - a.x) + s1.x * (r0.y - a.y)) / (-s2.x * s1.y + s1.x * s2.y);
		t = (s2.x * (r0.y - a.y) - s2.y * (r0.x - a.x)) / (-s2.x * s1.y + s1.x * s2.y);
		  
		if (s >= 0 && s <= 1 && t >= 0) {
		  return new Vector2(r0.x + (t * s1.x), r0.y + (t * s1.y));
		}
		return null;
	}
	
	public static boolean vectorInSector(Vector2 v, Sector s) {
		int j = s.points.length-1;
		boolean oddNodes = false;
		
		double x = v.x;
		double y = v.y;
		
		for (int i = 0; i < s.points.length; i++) {
			Vector2 p = s.points[i];
			Vector2 e = s.points[j];
			
			if ((p.y < y && e.y >= y
			  || e.y < y && p.y >= y)
			  && (p.x <= x || e.x <= x)) {
				oddNodes ^= (p.x + (y - p.y)/(e.y - p.y)*(e.x - p.x) < x);
			}
			j = i;
		}
		
		return oddNodes;
	}
}
