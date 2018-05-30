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
	private String errorMessage;
	
	public MapLoader(String path) {
		loadAndProcessMap(path);
	}
	
	Map<String,Sector> pendingSectors = new HashMap<String,Sector>();
	List<Wall> pendingWalls = new ArrayList<Wall>();
	List<Vector2> sectorPoints = new ArrayList<Vector2>();
	Map<String,Texture2> textures = new HashMap<String,Texture2>();
	List<Portal> portals = new ArrayList<Portal>();
	Map<String,WallTemplate> wallTemplates = new HashMap<String,WallTemplate>();
	
	Texture2 defaultTexture = Wall.DEFAULT_TEXTURE;
	
	Wall currentWall = null;
	
	Sector currentSector = null;
	String currentSectorName = null;
	String currentTemplateName = null;
	
	String lastDefinedTexName = null;
	
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
		
		int line = -1;
		for (String s : file) {
			line++;
			errorMessage = "ERROR at line " + line + " while reading " + path + ": ";
			s = format(s);
			
			if (s.indexOf("//") != -1) {
				s = s.substring(0,s.indexOf("//"));
			}
			
			if (beginsWith(s,"wall:")) {
				String coords = s.substring(s.indexOf(": ")+2);
				String[] params = coords.split(",");
				if (params.length == 4 || params.length == 5) {
					float x1 = Float.parseFloat(params[0]);
					float y1 = Float.parseFloat(params[1]);
					float x2 = Float.parseFloat(params[2]);
					float y2 = Float.parseFloat(params[3]);
					currentWall = new Wall(x1,y1,x2,y2);
				} else {
					error("wall definition must have either 4 or 5 parameters!");
				}
				
				//fifth parameter is a wall template
				if (params.length == 5) {
					String templateName = params[4];
					WallTemplate template = wallTemplates.get(templateName);
					if (template == null) {
						error("specified wall template does not exist!");
					} else {
						currentWall.xTiles = template.xTiles;
						currentWall.yTiles = template.yTiles;
						if (template.texname != null) {
							Texture2 tex = textures.get(template.texname);
							if (tex == null) {
								error("texture specified in wall template " + templateName + " is not a valid texture!");
							} else {
								currentWall.texture = tex;
							}
						} else {
							currentWall.texture = defaultTexture;
						}
					}
					pendingWalls.add(currentWall);
					currentWall = null;
				}
				continue;
			}
			
			if (beginsWith(s,"endwall")) {
				if (currentWall == null) {
					error("endwall must end a wall definition!");
				} else {
					pendingWalls.add(currentWall);
					currentWall = null;
				}
				continue;
			}
			
			if (beginsWith(s,"settex:")) {
				String texname = "";
				if (s.indexOf(": ") != -1) {
					texname = s.substring(s.indexOf(": ")+2);
					lastDefinedTexName = texname;
					Texture2 tex = textures.get(texname);
					if (tex == null) {
						error("no texture named \"" + texname + "\" was found!");
					} else if (currentWall == null) {
						error("settex must be within a wall definition!");
					} else {
						currentWall.setTexture(tex);
					}
				} else {
					error("\"settex\" should be followed by a valid texture name, previously defined with deftex!");
				}
				continue;
			}
			
			if (beginsWith(s,"tile:")) {
				String tilevals = s.substring(s.indexOf(": ")+2);
				String[] params = tilevals.split(",");
				if (currentWall == null) {
					error("tile must be within a valid wall or walltemplate definition!");
				}
				if (params.length==2 && currentWall != null) {
					float xTiles = Float.parseFloat(params[0]);
					float yTiles = Float.parseFloat(params[1]);
					currentWall.tile(xTiles, yTiles);
				} else {
					error("tile must have 2 and only 2 float parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"deftex:")) {
				String string = s.substring(s.indexOf(": ")+2);
				String[] params = string.split(",");
				if (params.length==4) {
					textures.put(params[0],new Texture2(params[1],Integer.parseInt(params[2]),Integer.parseInt(params[3])));
				} else {
					error("deftex must have 4 and only 4 parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"sector") && !beginsWith(s,"sectorheight")) {
				if (s.indexOf(" ") == -1 || s.lastIndexOf(":") == -1) {
					error("\"sector\" should be followed by space, sector name, then colon!");
				} else {
					currentSectorName = s.substring(s.indexOf(" ")+1,s.lastIndexOf(":"));
					currentSector = new Sector();
				}
				continue;
			}
			
			if (beginsWith(s,"mapwidth:")) {
				width = Integer.parseInt(s.substring(s.indexOf(": ")+2));
				continue;
			}
			
			if (beginsWith(s,"mapheight:")) {
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
					error("enddefpts must be within a valid sector definition!");
				}
				continue;
			}
			
			if (beginsWith(s,"endsector")) {
				if (currentSector == null) {
					error("endsector must end a valid sector definition!");
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
			
			if (beginsWith(s,"portal:")) {
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
						error("invalid sector names!");
					} else {
						Portal p = new Portal(new Vector2(x1,y1), new Vector2(x2,y2));
						p.setBorders(s0, s1);
						portals.add(p);
					}
				} else {
					error("portal must have 2 and only 2 parameters!");
				}
				continue;
			}
			
			if (beginsWith(s,"walltemplate")) {
				if ("walltemplate  ".length() > s.length()) {
					error("walltemplate definition requires a name!");
				} else {
					currentWall = new Wall(0,0,0,0);
					currentTemplateName = s.substring(s.indexOf(" ")+1,s.indexOf(":"));
				}
				continue;
			}
			
			if (beginsWith(s,"endtemplate")) {
				if (currentTemplateName == null) {
					error("endtemplate must end a valid template definition!");
				} else {
					WallTemplate temp = new WallTemplate();
					temp.xTiles = currentWall.xTiles;
					temp.yTiles = currentWall.yTiles;
					temp.texname = lastDefinedTexName;

					lastDefinedTexName = null;
					
					wallTemplates.put(currentTemplateName, temp);
				}
				continue;
			}
			
			if (beginsWith(s,"wallheight:")) {
				if (currentSector == null) {
					error("wallheight must be used in a sector definition!");
				} else {
					if (s.indexOf(": ") != -1 && s.indexOf(": ")+2 < s.length()) {
						float wallHeight = Float.parseFloat(s.substring(s.indexOf(": ")+2));
						currentSector.setWallHeight(wallHeight);
					} else {
						error("wallheight definition is not formatted correctly!");
					}
				}
				continue;
			}
			
			if (beginsWith(s,"sectorheight:")) {
				if (currentSector == null) {
					error("sectorheight must be used in a sector definition!");
				} else {
					if (s.indexOf(": ") != -1 && s.indexOf(": ")+2 < s.length()) {
						float sectorOffset = Float.parseFloat(s.substring(s.indexOf(": ")+2));
						currentSector.moveUp(sectorOffset);
					} else {
						error("sectorheight definition is not formatted correctly!");
					}
				}
				continue;
			}
			
			if (beginsWith(s,"import texturedefs ")) {
				if (s.indexOf(": ") != -1 && s.length() > s.indexOf(": ")+2) {
					String[] args = s.split(" ");
					String namespace = null;
					if (args[2].indexOf(":") != -1) {
						namespace = args[2].substring(0,args[2].indexOf(":"));
						//Recursion!
						Map<String,Texture2> loadedTextures = new MapLoader(args[3]).finalTextureMap();
						for (Entry<String,Texture2> e : loadedTextures.entrySet()) {
							if (beginsWith(e.getKey(),namespace + ".")) {
								error("namespace " + namespace + " is already in use!");
								break;
							} else {
								textures.put(namespace + "." + e.getKey(), e.getValue());
							}
						}
					} else {
						error("import texturedefs statement is formatted incorrectly!");
					}
				} else {
					error("import texturedefs statement is formatted incorrectly!");
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
	
	private void error(Object ... args) {
		String description = "";
		for (Object o : args) {
			description += o;
		}
		Logger.log(errorMessage + description);
	}
	
	private class WallTemplate {
		float xTiles = 1;
		float yTiles = 1;
		String texname = null;
		
		public WallTemplate() {
			
		}
	}
	
	public RaycastMap finalMap() {
		return map;
	}
	
	public Map<String,Texture2> finalTextureMap() {
		return textures;
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
