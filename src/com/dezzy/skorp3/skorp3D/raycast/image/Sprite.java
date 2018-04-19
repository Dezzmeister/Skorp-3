package com.dezzy.skorp3.skorp3D.raycast.image;

import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

public final class Sprite implements Comparable<Sprite>, Cloneable {
	public static final int ALPHA = 0; //Black
	public int order;
	public double distance;
	
	public double x;
	public double y;
	
	public Texture texture;
	
	public int[] pixels;
	
	public Sprite(String path, int size) {
		texture = new Texture(path,size);
		pixels = texture.pixels;
	}
	
	private Sprite() {
		
	}
	
	public Sprite at(double _x, double _y) {
		x = _x;
		y = _y;
		return this;
	}

	@Override
	public int compareTo(Sprite arg0) {
		if (distance > arg0.distance) {
			return -1;
		} else {
			return 1;
		}
	}
	
	@Override
	public Sprite clone() {
		Sprite s = new Sprite();
		s.x = x;
		s.y = y;
		s.pixels = pixels;
		s.texture = texture;
		return s;
	}
}
