package com.dezzy.skorp3.skorp3D.raycast.core;

import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

public class WorldMap {
	private Element[][] map;
	private Texture floorTexture = new Texture("assets/raycast/textures/floortiles.png",512).darken();
	private Texture ceilingTexture = new Texture("assets/raycast/textures/plaster.png",512);
	
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
}
