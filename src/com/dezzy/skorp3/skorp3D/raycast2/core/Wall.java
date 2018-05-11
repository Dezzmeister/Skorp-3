package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Color;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class Wall implements Linetype {
	public static final Texture2 defaultTexture = new Texture2("assets/raycast/textures/darkbricks.png",512,512);

	public Vector2 v0;
	public Vector2 v1;
	public float length;
	public int color = 0x00FFFFFF;
	public int shadeValue = 0;
	public Texture2 texture = defaultTexture;
	public float xTiles = 1;
	public float yTiles = 1;
	private boolean isPortal = false;
	
	public Wall(Vector2 _v0, Vector2 _v1, Color _color) {
		v0 = _v0;
		v1 = _v1;
		color = getIntFromRGB(_color);
		updateLength();
	}
	
	public Wall(Linetype line) {
		v0 = line.v0();
		v1 = line.v1();
		updateLength();
	}
	
	public Wall(float x1, float y1, float x2, float y2, Color _color) {
		v0 = new Vector2(x1,y1);
		v1 = new Vector2(x2,y2);
		color = getIntFromRGB(_color);
		updateLength();
	}
	
	public Wall(float x1, float y1, float x2, float y2) {
		v0 = new Vector2(x1,y1);
		v1 = new Vector2(x2,y2);
		updateLength();
	}
	
	public Wall(Vector2 _v0, Vector2 _v1) {
		v0 = _v0;
		v1 = _v1;
	}
	
	public Wall makePortal() {
		isPortal = true;
		return this;
	}
	
	public Wall setTexture(Texture2 _texture) {
		texture = _texture;
		return this;
	}
	
	public Texture2 getTexture() {
		return texture;
	}
	
	public void updateLength() {
		length = Vector2.distance(v0, v1);
	}
	
	public float slope() {
		float xDiff = v1.x-v0.x;
		float yDiff = v1.y-v0.y;
		return yDiff/xDiff;
	}
	
	public float yIntercept() {
		float x = v0.x;
		float y = v0.y;
		
		return y - (slope()*x);
	}
	
	public void setColor(Color _color) {
		color = getIntFromRGB(_color);
	}
	
	public Color getColor() {
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
		
		return new Color(red,green,blue);
	}
	
	/**
	 * Returns the angle between two Walls, in radians.
	 * 
	 * @param wall1 first wall
	 * @param wall2 second wall
	 * @return angle in radians
	 */
	public static float angleBetweenLines(Wall wall1, Wall wall2) {
		float angle1 = (float) Math.atan2(wall1.v0.y - wall1.v1.y, wall1.v0.x - wall1.v1.x);
		float angle2 = (float) Math.atan2(wall2.v0.y - wall2.v1.y, wall2.v0.x - wall2.v1.x);
		  
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
	
	public Wall shade(int darkenBy) {
		texture = new Texture2(texture.path,texture.width,texture.height);
		for (int i = 0; i < texture.pixels.length; i++) {
			if (texture.pixels[i] != Texture2.ALPHA) {
				Color c = new Color(texture.pixels[i]);
				int red = c.getRed()-darkenBy;
				int green = c.getGreen()-darkenBy;
				int blue = c.getBlue()-darkenBy;
				Color newcolor = new Color(red >= 0 ? red : 0, green >= 0 ? green : 0, blue >= 0 ? blue : 0);
				texture.pixels[i] = getIntFromRGB(newcolor);
			}
		}
		return this;
	}
	
	/**
	 * Allows a texture to be repeated multiple times (tiled) along a wall. By default, 
	 * xTiles is 1 and yTiles is 1, so the texture is stretched to fit the wall even
	 * if it may look ridiculous in doing so. Use this method to fix that: For example,
	 * if <code>tile(2,1)</code> is called, then this Wall's texture will appear twice on the Wall;
	 * both textures will be side-by-side. Likewise, if <code>tile(3,2)</code> is called, the texture will appear 6 times on the wall,
	 * arranged in a grid with a height of 2 and a width of 3. Each texture will be distorted to fit in a space 1/3 of the wall's width by
	 * 1/2 of its height.
	 * 
	 * @param _xTiles how many times to repeat the x texture
	 * @param _yTiles how many times to repeat the y texture
	 * @return this <code>Wall</code>
	 */
	public Wall tile(float _xTiles, float _yTiles) {
		xTiles = _xTiles;
		yTiles = _yTiles;
		return this;
	}
	
	public boolean isPortal() {
		return isPortal;
	}
	
	@Override
	public Vector2 v0() {
		return v0;
	}
	
	@Override
	public Vector2 v1() {
		return v1;
	}
	
	/**
	 * WARNING: This is not guaranteed to return the current length of the wall. If
	 * either of the wall's endpoints has changed and no call to <code>updateLength()</code> has
	 * been made, this will return an outdated length.
	 * <p>
	 * However, under normal use, this should not be a problem.
	 * 
	 * @return length of this Wall, as of last call to <code>updateLength()</code>
	 */
	@Override
	public float length() {
		return length;
	}
	
	@Override
	public float getNorm(Vector2 v) {
		return Vector2.distance(v, v0)/length;
	}
}
