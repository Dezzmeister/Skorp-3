package com.dezzy.skorp3.skorp3D.raycast2.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.dezzy.skorp3.file.Load;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastMap;
import com.dezzy.skorp3.skorp3D.raycast2.core.Sector;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

public class MapLoader {
	private RaycastMap map;
	
	public MapLoader(String path) {
		loadAndProcessMap(path);
	}
	
	private void loadAndProcessMap(String path) {
		Stream<String> fileStream = Load.load(path);
		List<String> file = new ArrayList<String>();
		fileStream.forEachOrdered(file::add);
		
		int width = 10;
		int height = 10;
		
		if (!file.get(0).equals("Dezzy")) {
			Logger.log("CRITICAL ERROR: First line in level must be \"Dezzy\"!");
			System.exit(0);
		}
		
		List<Sector> finalSectors = new ArrayList<Sector>();
		List<Wall> pendingWalls = new ArrayList<Wall>();
		List<Vector2> sectorPoints = new ArrayList<Vector2>();
		Map<String,Texture2> textures = new HashMap<String,Texture2>();
		
		Sector[] sectorArray;
		Wall[] walls;
		
		Texture2 defaultTex;
		Wall currentWall;
		
		for (String s : file) {
			s = format(s);
			
			if (beginsWith(s,"mapwidth")) {
				width = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			}
			
			if (beginsWith(s,"mapheight")) {
				height = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			}
			
			if (beginsWith(s,"pt")) {
				int x = Integer.parseInt(s.substring(s.indexOf(": ")+2,s.indexOf(",")));
				int y = Integer.parseInt(s.substring(s.indexOf(",")+1));
				sectorPoints.add(new Vector2(x,y));
			}
			
			if (beginsWith(s,"wall:")) {
				String coords = s.substring(s.indexOf(": ")+2);
				String[] array = coords.split(",");
				float x1 = Float.parseFloat(array[0]);
				float y1 = Float.parseFloat(array[1]);
				float x2 = Float.parseFloat(array[2]);
				float y2 = Float.parseFloat(array[3]);
				currentWall = new Wall(x1,y1,x2,y2);
			}
			
			if (beginsWith(s,"tile:")) {
				String tilevals = s.substring(s.indexOf(": ")+2);
			}
		}
	}
	
	/**
	 * Removes leading spaces of an input String.
	 * 
	 * @param s input String, potentially with leading spaces
	 * @return input formatted to have no leading spaces
	 */
	private String format(String s) {
		while(s.charAt(0)==' ') {
			s = s.substring(1);
		}
		
		return s;
	}
	
	private boolean beginsWith(String s, String keyword) {
		keyword = keyword.toLowerCase();
		return (s.length() >= keyword.length() && s.indexOf(keyword)==0);
	}
}
