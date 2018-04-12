package com.dezzy.skorp3.skorp3D.raycast.core;

public class WorldMap {
	private Element[][] map;
	
	public WorldMap(Element[][] worldMap) {
		map = worldMap;
	}
	
	public WorldMap(int[][] idMap, ElementTable governingTable) {
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
		return map[y][x];
	}
}
