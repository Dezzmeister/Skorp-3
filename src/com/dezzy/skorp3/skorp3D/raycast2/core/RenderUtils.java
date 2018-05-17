package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Color;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public final class RenderUtils {
	
	private RenderUtils() {
		throw new RuntimeException();
	}
	
	public static Vector2 rayHitSegment(Vector2 rayStart, Vector2 rayDirection, Linetype segment) {
		Vector2 r0 = rayStart;
		Vector2 r1 = rayDirection;
		Vector2 a = segment.v0();
		Vector2 b = segment.v1();
		
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
	
	public static Vector2 segmentHitSegment(Linetype segment1, Linetype segment2) {
		Vector2 r0 = segment1.v0();
		Vector2 r1 = segment1.v1();
		Vector2 a = segment2.v0();
		Vector2 b = segment2.v1();
		
		Vector2 s1,s2;
		s1 = new Vector2(r1.x-r0.x,r1.y-r0.y);
		s2 = new Vector2(b.x-a.x,b.y-a.y);
		  
		float s,t;
		s = (-s1.y * (r0.x - a.x) + s1.x * (r0.y - a.y)) / (-s2.x * s1.y + s1.x * s2.y);
		t = (s2.x * (r0.y - a.y) - s2.y * (r0.x - a.x)) / (-s2.x * s1.y + s1.x * s2.y);
		  
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
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
	
	/**
	 * Recursively darkens a color with bitwise operations.
	 * 
	 * @param color RGB int
	 * @param times how many times to darken
	 * @return a color with same hue as color that has been darkened "times" times
	 */
	public static int darken(int color, int times) {
		if (times == 0) {
			return color;
		}
		
		return (darken(color,times-1) >> 1) & 8355711;
	}
	
	/**
	 * Returns the angle between two Walls, in radians.
	 * 
	 * @param wall1 first wall
	 * @param wall2 second wall
	 * @return angle in radians
	 */
	public static float angleBetweenLines(Linetype wall1, Linetype wall2) {
		float angle1 = (float) Math.atan2(wall1.v0().y - wall1.v1().y, wall1.v0().x - wall1.v1().x);
		float angle2 = (float) Math.atan2(wall2.v0().y - wall2.v1().y, wall2.v0().x - wall2.v1().x);
		  
		return Math.abs(angle1-angle2);
	}
	
	public static int getIntFromRGB(Color color) {
		int rgb = color.getRed();
		rgb = (rgb << 8) + color.getGreen();
		rgb = (rgb << 8) + color.getBlue();
		
		return rgb;
	}
	
	public static Color getRGBFromInt(int col) {
		int red = (col >> 16) & 0xFF;
		int green = (col >> 8) & 0xFF;
		int blue = col & 0xFF;
		
		return new Color(red,green,blue);
	}
}
