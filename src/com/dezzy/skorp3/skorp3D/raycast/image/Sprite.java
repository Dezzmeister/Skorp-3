package com.dezzy.skorp3.skorp3D.raycast.image;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

public final class Sprite implements Comparable<Sprite>, Cloneable {
	/**
	 * The alpha color is used during rendering to determine which pixels of the image 
	 * should not be visible (transparent). Because the BufferedImage used during rendering does not support 
	 * the alpha channel, a color must be defined here that represents a 100% transparent pixel in the image.
	 */
	public int alpha = 0xFF000000;
	public int order;
	public double distance;
	
	public double x;
	public double y;
	
	public Texture texture;
	public Vector2 dir;
	
	public int xDrawBegin = 0;
	public int xDrawEnd;
	public int yDrawBegin = 0;
	public int yDrawEnd;
	
	public int[] pixels;
	
	public Sprite(String path, int size) {
		texture = new Texture(path,size);
		pixels = texture.pixels;
		xDrawEnd = size;
		yDrawEnd = size;
	}
	
	private Sprite() {
		
	}
	
	public Sprite at(double _x, double _y) {
		x = _x;
		y = _y;
		return this;
	}
	
	public Sprite lookingAt(Vector2 _dir) {
		dir = _dir;
		return this;
	}
	
	public int width() {
		return xDrawEnd - xDrawBegin;
	}
	
	public int height() {
		return yDrawEnd - yDrawBegin;
	}
	
	/**
	 * Manually sets the rectangular portion of the sprite that will be tested during rendering.
	 * By default, this rectangle encompasses the entire sprite. However, it can be resized to speed up
	 * the rendering process. 
	 * <p>
	 * For example, if the entire upper portion of the sprite is transparent,
	 * the drawable bounds can be resized to avoid testing that portion of the image at all.
	 * 
	 * @param _xBegin leftmost x bound
	 * @param _yBegin upper y bound
	 * @param _xEnd rightmost x bound
	 * @param _yEnd lower y bound
	 * @return this Sprite
	 */
	public Sprite setDrawableBounds(int _xBegin, int _yBegin, int _xEnd, int _yEnd) {
		xDrawBegin = _xBegin;
		yDrawBegin = _yBegin;
		xDrawEnd = _xEnd;
		yDrawEnd = _yEnd;
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
		s.dir = dir;
		s.xDrawBegin = xDrawBegin;
		s.xDrawEnd = xDrawEnd;
		s.yDrawBegin = yDrawBegin;
		s.yDrawEnd = yDrawEnd;
		s.alpha = alpha;
		return s;
	}
}
