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
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.game3D.VBO3DList;
import com.dezzy.skorp3.skorp3D.data.VBO;
import com.dezzy.skorp3.skorp3D.data.VBOList;
import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.ElementTable;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.image.Sprite;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;
import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
@SuppressWarnings("unused")
public final class Global {
	/**
	 * X resolution of the rendered image
	 */
	public static final int WIDTH = 500;
	/**
	 * Y resolution of the rendered image
	 */
	public static final int HEIGHT = 500;
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
	
	public static class Raycast {
		public static WorldMap mainMap;
		public static Camera camera = new Camera()
								      .setPos(new Vector(5,5))
								      .setDir(new Vector(-0.75,0))
								      .setPlane(new Vector(0,0.5));
		
		static {
			ElementTable table = new ElementTable();
			
			Texture nicebricks = new Texture("assets/raycast/textures/nicebricks.png",256);
			Texture darkbricks = new Texture("assets/raycast/textures/darkbricks.png",512);
			Texture tiles = new Texture("assets/raycast/textures/tiles.png",512);
			Texture metal = new Texture("assets/raycast/textures/metal.png",512);
			Texture wood = new Texture("assets/raycast/textures/wood.png",512);
			
			table.add(new Element(1,"border",Color.ORANGE,false).applyTexture(darkbricks));
			table.add(new Element(2,"green",Color.GREEN,true).applyTexture(nicebricks));
			table.add(new Element(3,"red",Color.RED,true).applyTexture(wood));
			table.add(new Element(4,"magenta",Color.MAGENTA,true).applyTexture(metal));
			table.add(new Element(5,"gold",Color.YELLOW,false).applyTexture(tiles));
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
					{1,1,1,1,1,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1}
			};
			
			Sprite test = new Sprite("assets/raycast/sprites/test.png",16).at(5, 5);
			Sprite render2 = new Sprite("assets/raycast/sprites/render2.png",256).at(7.5, 7.5);
			Sprite angrydude = new Sprite("assets/raycast/sprites/angrydude.png",64).at(8.5, 1.5);
			Sprite weirdThing = new Sprite("assets/raycast/sprites/weirdthing.png",64).at(3, 2);
			
			mainMap = new WorldMap(testroom,table);
			mainMap.startAt(2, 2);
			mainMap.applyStartPos(camera);
			mainMap.addSprite(test);
			mainMap.addSprite(render2);
			mainMap.addSprite(angrydude);
			mainMap.addSprite(weirdThing);
		}
	}
}
