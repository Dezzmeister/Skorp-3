package com.dezzy.skorp3.skorp3D.raycast.core;

import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

public class WorldMap {
	private Element[][] map;
	private Texture floorTexture = new Texture("assets/raycast/textures/hardwood.png",512).darken();
	private Texture ceilingTexture = new Texture("assets/raycast/textures/floortiles.png",512);
	
	public WorldMap(Element[][] worldMap) {
		map = worldMap;
	}
	
	public WorldMap(int[][] idMap, ElementTable governingTable) {
		map = new Element[idMap.length][idMap[0].length];
		
		for (int row = 0; row < idMap.length; row++) {
			for (int col = 0; col < idMap[0].length; col++) {
				map[row][col] = governingTable.getByID(idMap[row][col]);
			}
		}
	}
	
	public WorldMap(String[][] nameMap, ElementTable governingTable) {
		for (int row = 0; row < nameMap.length; row++) {
			for (int col = 0; col < nameMap[0].length; col++) {
				map[row][col] = governingTable.getByName(nameMap[row][col]);
			}
		}
	}
	
	public Element get(int x, int y) {
		return map[x][y];
	}
	
	/**
	 * Replaces all border Elements with an unchangeable version of the specified Element.
	 * @param element Element to set border to
	 */
	public void setBorder(Element element) {
		Element e = new Element(element.id(),element.name(),element.color(),false);
		for (int col = 0; col < map[0].length; col++) {
			map[0][col] = e;
			map[map.length-1][col] = e;
		}
		
		for (int row = 0; row < map.length; row++) {
			map[row][0] = e;
			map[row][map[0].length-1] = e;
		}
	}
	
	public int width() {
		return map[0].length;
	}
	
	public int height() {
		return map.length;
	}
	
	public boolean set(int x, int y, Element element) {
		return get(x,y).tryChange(element);
	}
	
	/**
	 * Sets the floor texture to a slightly darkened version of the <code>Texture</code>
	 * provided.
	 * 
	 * @param _texture Texture to be set to floor texture
	 * @return this WorldMap
	 */
	public WorldMap setFloorTexture(Texture _texture) {
		floorTexture = _texture.darken();
		return this;
	}
	
	public WorldMap setCeilingTexture(Texture _texture) {
		ceilingTexture = _texture;
		return this;
	}
	
	public Texture floorTexture() {
		return floorTexture;
	}
	
	public Texture ceilingTexture() {
		return ceilingTexture;
	}
	
	public int[][] getIDArray() {
		int[][] result = new int[width()][height()];
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < width(); y++) {
				result[x][y] = map[x][y].id();
			}
		}
		return result;
	}
	
	/**
	 * Returns a 4D array containing every element's front and side texture pixels. The first coordinate is the x value,
	 * the second coordinate is the y value, the third coordinate is either 0 or 1, (corresponding to either front or side array), and
	 * the array at that index is the texture's pixels.
	 * <p>
	 * Example:
	 * <code>int i = getTextureArrays()[20][44][1][450];</code>
	 * <code>[20][44]</code> is the Element at x position 20, y position 44. <code>[1]</code> specifies the side texture,
	 * and <code>[450]</code> specifies the 450th pixel in this texture.
	 * <p>
	 * This is used in the <code>GPURaycaster</code>, which cannot process reference variables like the standard <code>Raycaster</code> can.
	 * 
	 * @return An array containing every element's front and side texture pixels
	 */
	public int[][][][] getTextureArrays() {
		int[][][][] result = new int[width()][height()][2][];
		
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < width(); y++) {
				result[x][y][0] = map[x][y].frontTexture().pixels;
				result[x][y][1] = map[x][y].sideTexture().pixels;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a 3D array containing every element's texture sizes. The first two coordinates represent the x and y coordinate of the Element on the map,
	 * and the third coordinate is either 0 for front texture or 1 for side texture.
	 * <p>
	 * Example:
	 * <code>int i = getTextureSizeMap()[14][35][0];</code>
	 * <code>i</code> now holds the size of the front texture of the <code>Element</code> located at x = 14, y = 35.
	 * 
	 * @return A map of all the texture sizes
	 */
	public int[][][] getTextureSizeMap() {
		int[][][] result = new int[width()][height()][2];
		
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < height(); y++) {
				result[x][y][0] = map[x][y].frontTexture().SIZE;
				result[x][y][1] = map[x][y].sideTexture().SIZE;
			}
		}
		
		return result;
	}
}
