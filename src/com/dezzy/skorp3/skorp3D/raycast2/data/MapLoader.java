package com.dezzy.skorp3.skorp3D.raycast2.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.dezzy.skorp3.file.Load;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.core.Portal;
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
		
		Map<String,Sector> pendingSectors = new HashMap<String,Sector>();
		List<Wall> pendingWalls = new ArrayList<Wall>();
		List<Vector2> sectorPoints = new ArrayList<Vector2>();
		Map<String,Texture2> textures = new HashMap<String,Texture2>();
		List<Portal> portals = new ArrayList<Portal>();
		
		Texture2 defaultTex;
		Wall currentWall = null;
		
		Sector currentSector = null;
		String currentSectorName = null;
		
		int line = -1;
		for (String s : file) {
			line++;
			String errorMessage = "ERROR at line " + line + " while reading " + path + ": ";
			s = format(s);
			
			if (beginsWith(s,"wall:")) {
				String coords = s.substring(s.indexOf(": ")+2);
				String[] params = coords.split(",");
				if (params.length == 4) {
					float x1 = Float.parseFloat(params[0]);
					float y1 = Float.parseFloat(params[1]);
					float x2 = Float.parseFloat(params[2]);
					float y2 = Float.parseFloat(params[3]);
					currentWall = new Wall(x1,y1,x2,y2);
				} else {
					Logger.log(errorMessage + "wall must have 4 and only 4 parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"endwall")) {
				if (currentWall == null) {
					Logger.log(errorMessage + "endwall must end a wall definition!");
				} else {
					pendingWalls.add(currentWall);
					currentWall = null;
				}
				continue;
			}
			
			if (beginsWith(s,"settex")) {
				String texname = "";
				if (s.indexOf(": ") != -1) {
					texname = s.substring(s.indexOf(": ")+2);
					Texture2 tex = textures.get(texname);
					if (tex == null) {
						Logger.log(errorMessage + "no texture named \"" + texname + "\" was found!");
					} else if (currentWall == null) {
						Logger.log(errorMessage + "settex must be within a wall definition!");
					} else {
						currentWall.setTexture(tex);
					}
				} else {
					Logger.log(errorMessage + "\"settex\" should be followed by a valid texture name, previously defined with deftex!");
				}
				continue;
			}
			
			if (beginsWith(s,"tile")) {
				String tilevals = s.substring(s.indexOf(": ")+2);
				String[] params = tilevals.split(",");
				if (currentWall == null) {
					Logger.log(errorMessage + "tile must be within a wall definition!");
				}
				if (params.length==2) {
					float xTiles = Float.parseFloat(params[0]);
					float yTiles = Float.parseFloat(params[1]);
					currentWall.tile(xTiles, yTiles);
				} else {
					Logger.log(errorMessage + "tile must have 2 and only 2 float parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"deftex")) {
				String string = s.substring(s.indexOf(": ")+2);
				String[] params = string.split(",");
				if (params.length==4) {
					System.out.println(params[0]);
					textures.put(params[0],new Texture2(params[1],Integer.parseInt(params[2]),Integer.parseInt(params[3])));
				} else {
					Logger.log(errorMessage + "deftex must have 4 and only 4 parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"sector")) {
				if (s.indexOf(" ") == -1 || s.lastIndexOf(":") == -1) {
					Logger.log(errorMessage + "\"sector\" should be followed by space, sector name, then colon!");
				} else {
					currentSectorName = s.substring(s.indexOf(" ")+1,s.lastIndexOf(":"));
					currentSector = new Sector();
				}
				continue;
			}
			
			if (beginsWith(s,"mapwidth")) {
				width = Integer.parseInt(s.substring(s.indexOf(": ")+2));
				continue;
			}
			
			if (beginsWith(s,"mapheight")) {
				height = Integer.parseInt(s.substring(s.indexOf(": ")+2));
				continue;
			}
			
			if (beginsWith(s,"pt")) {
				float x = Float.parseFloat(s.substring(s.indexOf(": ")+2,s.indexOf(",")));
				float y = Float.parseFloat(s.substring(s.indexOf(",")+1));

				sectorPoints.add(new Vector2(x,y));
				continue;
			}
			
			if (beginsWith(s,"enddefpts")) {
				Vector2[] secpts = new Vector2[sectorPoints.size()];
				for (int i = 0; i < secpts.length; i++) {
					secpts[i] = sectorPoints.get(i);
				}
				sectorPoints.clear();
				if (currentSector != null) {
					currentSector.points = secpts;
				} else {
					Logger.log(errorMessage + "enddefpts must be within a sector definition!");
				}
				continue;
			}
			
			if (beginsWith(s,"endsector")) {
				if (currentSector == null) {
					Logger.log(errorMessage + "endsector must end a sector definition!");
				} else {
					Wall[] walls = new Wall[pendingWalls.size()];
					for (int i = 0; i < walls.length; i++) {
						walls[i] = pendingWalls.get(i);
					}
					currentSector.walls = walls;
					pendingWalls.clear();
					pendingSectors.put(currentSectorName, currentSector);
					currentSectorName = null;
					currentSector = null;
				}
				continue;
			}
			
			if (beginsWith(s,"portal")) {
				String string = s.substring(s.indexOf(": ")+2);
				String[] params = string.split(",");
				if (params.length == 6) {
					float x1 = Float.parseFloat(params[0]);
					float y1 = Float.parseFloat(params[1]);
					float x2 = Float.parseFloat(params[2]);
					float y2 = Float.parseFloat(params[3]);
					
					Sector s0 = pendingSectors.get(params[4]);
					Sector s1 = pendingSectors.get(params[5]);
					
					if (s0 == null || s1 == null) {
						Logger.log(errorMessage + "invalid sector names!");
					} else {
						Portal p = new Portal(new Vector2(x1,y1), new Vector2(x2,y2));
						p.setBorders(s0, s1);
						portals.add(p);
					}
				} else {
					Logger.log(errorMessage + "portal must have 2 and only 2 parameters!");
				}
				continue;
			}
		}
		
		Sector[] finalSectors = new Sector[pendingSectors.size()];
		int index = 0;
		for (Entry<String,Sector> entry : pendingSectors.entrySet()) {
			finalSectors[index] = entry.getValue();
			index++;
		}
		Portal[] finalPortals = new Portal[portals.size()];
		for (int i = 0; i < finalPortals.length; i++) {
			finalPortals[i] = portals.get(i);
		}
		
		map = new RaycastMap(width,height,finalSectors).definePortals(finalPortals);
	}
	
	public RaycastMap finalMap() {
		return map;
	}
	
	/**
	 * Removes leading spaces of an input String.
	 * 
	 * @param s input String, potentially with leading spaces
	 * @return input formatted to have no leading spaces
	 */
	private String format(String s) {
		if (s.length() == 0) {
			return "";
		}
		
		if (s.charAt(0)==' ' || s.charAt(0) == '\t') {
			return format(s.substring(1));
		}
		
		return s;
	}
	
	private boolean beginsWith(String s, String keyword) {
		keyword = keyword.toLowerCase();
		return (s.length() >= keyword.length() && s.indexOf(keyword)==0);
	}
}
