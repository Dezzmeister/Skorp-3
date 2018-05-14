package com.dezzy.skorp3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.UI.MouseRobot;
import com.dezzy.skorp3.annotations.Processors;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.game3D.VBO3DList;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.data.VBO;
import com.dezzy.skorp3.skorp3D.data.VBOList;
import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.ElementTable;
import com.dezzy.skorp3.skorp3D.raycast.core.Orientation;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.image.Sprite;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;
import com.dezzy.skorp3.skorp3D.raycast.render.Texture;
import com.dezzy.skorp3.skorp3D.raycast2.core.Portal;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastMap;
import com.dezzy.skorp3.skorp3D.raycast2.core.Sector;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;
import com.dezzy.skorp3.skorp3D.raycast2.data.MapLoader;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;
import com.dezzy.skorp3.skorp3D.true3D2.core.Mesh;
import com.dezzy.skorp3.skorp3D.true3D2.core.MeshList;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
@SuppressWarnings("unused")
public final class Global {
	private static final int SIZE = 300;
	/**
	 * X resolution of the rendered image
	 */
	public static final int WIDTH = SIZE;
	/**
	 * Y resolution of the rendered image
	 */
	public static final int HEIGHT = SIZE;
	/**
	 * X resolution of the displayed image
	 */
	public static final int SCREENWIDTH = 1000;
	/**
	 * Y resolution of the displayed image
	 */
	public static final int SCREENHEIGHT = 1000;
	
	public static final VBO3D mainVBO = new VBO3D("main");
	
	public static final JFrame frame = new JFrame();
	public static final Container pane = frame.getContentPane();
	public static final Mouse mouseData = new MouseRobot(SCREENWIDTH,SCREENHEIGHT,pane);
	public static final boolean[] keys = new boolean[256];
	
	//public static final Keyboard keyboard = new Keyboard();
	
	static {
		Processors.activate();
		True3D.renderList.add(mainVBO);
		
		True3D.VBOLIST.add(True3D.VBO);
		
		BufferedImage cursorImg = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "blank cursor");
		pane.setCursor(blankCursor);
		//frame.addKeyListener(keyboard);
	}
	
	private Global() {

	}
	
	public static class True3D {
		public static final VBO3DList renderList = new VBO3DList();
		public static final Data3D data3D = new Data3D(false,70);
		
		public static final VBO VBO = new VBO();
		public static final VBOList VBOLIST = new VBOList();
	}
	
	public static class True3D2 {
		public static MeshList meshlist = new MeshList();
		public static Mesh testmesh = new Mesh(3);
		public static com.dezzy.skorp3.skorp3D.true3D2.render.Camera camera = new com.dezzy.skorp3.skorp3D.true3D2.render.Camera(new Vertex(0,0,0), new Vertex(0,0,-1));
		
		static {
			testmesh.set(0,100,-100,-100);
			testmesh.set(1,200,-100,-100);
			testmesh.set(2,150,-200,-100);
			
			meshlist.add(testmesh);
		}
	}
	
	public static class Raycast2 {
		public static final Texture2 cartoonstones = new Texture2("assets/raycast/textures/cartoonstones.png",512,512);
		public static final Texture2 bars = new Texture2("assets/raycast/textures/bars.png",16,16);
		public static final Texture2 window = new Texture2("assets/raycast/textures/window.png",16,16);
		//public static final Texture2 brix = new Texture2("assets/raycast/textures/brix.png",512,512);
		public static final Texture2 rockface = new Texture2("assets/raycast/textures/rock.png",2000,2000);
		
		public static final Sector mainSector = new Sector(new Vector2(0,0),
														   new Vector2(0,10),
														   new Vector2(10,10),
														   new Vector2(10,0))
														   .defineWalls(
																   new Wall(0,0,10,0).tile(8, 1),
															   	   //new Wall(0,0,0,10).tile(8, 1),
															       new Wall(10,0,10,10).tile(8, 1),
															   	   new Wall(0,10,10,10).tile(8, 1),
															   	   new Wall(0,1,1,1).setTexture(cartoonstones),
															   	   new Wall(1,1,4,4).setTexture(cartoonstones),
															       new Wall(4,4,4,7).setTexture(cartoonstones),
															   	   new Wall(4,7,4,8).setTexture(bars),
															   	   new Wall(4,8,4,9.25f).setTexture(window),
															   	   new Wall(4,9.25f,9,9.25f).setTexture(window).tile(4, 1));
		public static final Sector testSector = new Sector(new Vector2(0,0),
														   new Vector2(0,10),
														   new Vector2(-10,10),
														   new Vector2(-10,0))
														   .defineWalls(
																   new Wall(0,0,-10,0).tile(8, 1),
																   //new Wall(-10,0,-10,10).tile(8, 1).setTexture(cartoonstones),
																   new Wall(-10,10,0,10).tile(8, 1).setTexture(cartoonstones));
		
		public static final Sector testSector2 = new Sector(new Vector2(-10,0),
															new Vector2(-20,0),
															new Vector2(-20,10),
															new Vector2(-10,10))
															.defineWalls(
																	new Wall(-10,0,-20,0).tile(8, 1).setTexture(rockface),
																	new Wall(-20,0,-20,10).tile(8, 1).setTexture(rockface),
																	new Wall(-20,10,-10,10).tile(8, 1).setTexture(rockface));
		
		public static RaycastMap map = new RaycastMap(10,10,mainSector,testSector,testSector2)
													 .definePortals(
															 new Portal(0,0,0,10).setBorders(mainSector, testSector),
															 new Portal(-10,0,-10,10).setBorders(testSector, testSector2));
		
		public static RaycastMap loadedmap = new MapLoader("C:/Users/Joe Junior/git/Skorp-3/levels/test.lol").finalMap();
	}
	
	public static class Raycast {
		public static WorldMap mainMap;
		public static Camera camera = new Camera()
								      .setPos(new Vector2(1,1))
								      .setDir(new Vector2(-0.75f,0))
								      .setPlane(new Vector2(0,0.5f));
		
		static {
			ElementTable table = new ElementTable();
			
			final Texture nicebricks = new Texture("assets/raycast/textures/nicebricks.png",256);
			final Texture darkbricks = new Texture("assets/raycast/textures/darkbricks.png",512);
			final Texture tiles = new Texture("assets/raycast/textures/tiles.png",512);
			final Texture metal = new Texture("assets/raycast/textures/metal.png",512);
			final Texture wood = new Texture("assets/raycast/textures/wood.png",512);
			
			table.add(new Element(1,"border",Color.ORANGE,false).applyTexture(darkbricks));
			table.add(new Element(2,"green",Color.GREEN,true).applyTexture(nicebricks));
			table.add(new Element(3,"red",Color.RED,true).applyTexture(wood));
			table.add(new Element(4,"magenta",Color.MAGENTA,true).applyTexture(metal));
			table.add(new Element(5,"gold",Color.YELLOW,false).applyTexture(tiles));
			table.add(new Element(6,"halftest",Color.GREEN,false).applyTexture(nicebricks).makeThin(Orientation.XPLANE, new Line(0.5,0,0.5,1)));
			/**
			int[][] map = {
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,1,0,1},
					{1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,2,2,2,2,2,0,0,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,1,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,0,1,0,1,0,2,2,2,2,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,0,1},
					{1,0,0,0,0,0,0,1,0,1,0,2,0,0,0,0,2,0,0,0,2,2,2,2,2,0,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,1,0,0,0,0,0,1,0,1},
					{1,0,0,1,1,1,1,1,0,1,0,2,0,2,0,0,2,0,0,0,2,0,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,1,0,0,1,0,1,1,0,1,1,0,1},
					{1,0,0,0,1,0,0,0,0,1,0,2,0,2,0,0,0,0,0,0,2,0,0,2,0,0,1,1,0,1,1,0,1,0,1,1,1,0,0,1,0,1,0,1,0,0,0,1,0,1},
					{1,0,0,0,1,0,1,1,0,1,0,2,0,2,2,2,2,2,2,0,2,0,0,2,1,0,0,1,0,1,1,0,1,0,1,0,0,0,1,0,0,1,0,1,1,0,1,0,0,1},
					{1,0,0,0,0,0,0,1,0,1,0,2,0,0,0,0,2,0,0,0,0,0,0,2,0,0,1,1,0,1,0,1,1,0,0,0,1,0,1,0,1,0,0,0,0,1,0,1,1,1},
					{1,0,0,1,1,1,1,1,0,0,0,0,0,2,2,2,0,2,2,2,2,0,0,2,1,0,0,1,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0,0,0,0,1,0,1},
					{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1,1,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,1,0,0,1,0,0,1},
					{1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,2,2,2,2,0,0,2,1,0,0,1,0,1,1,1,0,1,0,0,0,0,1,0,1,0,1,0,0,1,0,1,0,1},
					{1,0,0,0,1,0,0,1,0,0,1,1,1,0,0,2,0,2,0,0,0,0,0,2,0,0,0,1,0,0,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,0,0,1,0,1},
					{1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,2,2,2,2,2,2,2,0,0,0,1,0,1,0,1,0,1,0,1,0,0,1,0,1,1,0,1,0,1,0,1,0,1},
					{1,0,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,0,1,1,1,0,0,1,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,3,0,3,0,3,0,3,0,3,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,1,0,1,1,1,0,0,1,0,1,1,0,1,0,1},
					{1,0,2,2,2,2,0,0,2,0,0,2,2,2,2,0,0,3,3,3,0,0,3,0,0,3,3,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0,1},
					{1,0,0,0,2,0,0,2,0,2,0,0,0,2,0,0,0,3,0,0,0,3,0,3,0,3,0,3,0,1,1,1,0,1,0,1,0,1,1,1,1,0,1,0,0,0,0,1,0,1},
					{1,0,0,0,2,0,0,2,0,2,0,0,0,2,0,0,0,3,3,0,0,3,0,3,0,3,3,0,0,1,0,1,0,1,1,1,0,1,0,1,0,0,1,0,1,1,0,1,0,1},
					{1,0,2,0,2,0,0,2,0,2,0,2,0,2,0,0,0,3,0,0,0,3,0,3,0,3,0,3,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1},
					{1,0,0,2,0,0,0,0,2,0,0,0,2,0,0,0,0,3,0,0,0,0,3,0,0,3,0,3,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,0,1,1,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,4,0,0,4,4,4,4,4,4,4,0,0,4,4,4,4,4,4,4,4,1},
					{1,0,1,1,1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0,0,4,0,4,0,0,0,4,0,0,4,0,1,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,4,0,0,4,0,4,0,4,4,4,4,0,4,0,1,0,1,1,1,1,1},
					{1,0,1,0,1,0,1,1,1,0,1,1,1,0,0,1,1,0,0,0,1,0,0,1,1,0,0,0,0,4,0,0,4,0,4,0,4,0,0,0,0,4,0,1,0,1,0,1,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,4,0,4,0,4,4,0,4,4,4,0,1,0,1,0,0,0,1},
					{1,0,3,0,0,0,0,3,0,0,3,0,0,0,3,0,0,0,2,2,2,2,2,2,2,0,0,0,0,4,0,0,4,0,4,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1},
					{1,0,3,0,0,0,3,0,3,0,3,0,0,0,3,0,0,0,2,0,0,0,0,0,2,0,0,0,0,4,0,0,4,0,4,4,4,4,4,4,4,4,4,1,0,1,0,0,0,1},
					{1,0,3,0,0,0,3,0,3,0,3,0,0,0,3,0,0,0,2,0,0,0,0,0,2,0,0,0,0,4,0,0,4,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
					{1,0,3,0,0,0,3,0,3,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,4,0,0,4,0,4,4,4,4,4,4,4,4,0,1,1,1,1,1,1,1},
					{1,0,3,3,3,0,0,3,0,0,3,3,3,0,3,0,0,0,2,0,0,0,0,0,2,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,2,0,0,0,5,0,5,0,5,0,5,0,5,0,1},
					{1,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,5,0,5,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,2,0,2,0,0,0,5,0,5,0,5,0,5,0,5,1},
					{1,0,3,3,3,3,0,3,3,3,3,3,3,3,0,3,3,3,3,3,3,3,0,3,3,3,3,3,3,5,0,0,2,0,0,2,0,0,0,5,0,5,0,5,0,5,0,5,0,1},
					{1,0,3,0,3,0,0,0,0,0,0,0,0,3,0,3,0,0,0,3,0,3,0,0,0,3,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,3,0,3,0,3,0,0,0,0,0,0,3,0,3,0,0,0,0,0,3,3,3,0,3,0,3,3,5,0,0,2,0,5,0,0,5,0,5,5,5,5,5,5,5,5,5,5,1},
					{1,0,3,0,0,0,3,3,3,3,3,3,0,0,0,0,0,0,0,3,0,0,0,3,0,3,0,3,0,0,5,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,1},
					{1,0,3,3,3,3,3,0,0,0,3,0,0,0,3,3,3,3,3,3,3,3,3,3,0,3,0,3,0,0,0,5,0,0,5,0,5,0,0,5,0,5,5,5,5,5,5,5,0,1},
					{1,0,3,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3,0,1,0,0,5,0,0,5,0,5,0,5,0,5,0,0,0,0,0,5,0,1},
					{1,0,0,0,0,0,0,0,0,0,3,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,3,0,1,1,1,1,5,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,1},
					{1,0,3,3,3,3,3,0,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0,1},
					{1,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

			};
			**/
			int[][] testroom = {
					{1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,6,1,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1}
			};
			
			Sprite test = new Sprite("assets/raycast/sprites/test.png",16).at(5, 5);
			Sprite render2 = new Sprite("assets/raycast/sprites/render2.png",256).at(7.5, 7.5);
			Sprite angrydude = new Sprite("assets/raycast/sprites/angrydude.png",64).at(8.5, 1.5);
			Sprite weirdThing = new Sprite("assets/raycast/sprites/weirdthing.png",64).at(3, 2);
			
			mainMap = new WorldMap(testroom,table);
			mainMap.setBorder(new Element(3,"red",Color.RED,true).applyTexture(wood));
			mainMap.startAt(2, 2);
			mainMap.applyStartPos(camera);
			mainMap.addSprite(test);
			mainMap.addSprite(render2);
			mainMap.addSprite(angrydude);
			mainMap.addSprite(weirdThing);
		}
	}
}
