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
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
public final class Global {
	public static final int WIDTH = 1800;
	public static final int HEIGHT = 1000;
	
	public static final VBO3D mainVBO = new VBO3D("main");
	
	public static final JFrame frame = new JFrame();
	public static final Container pane = frame.getContentPane();
	public static final Mouse mouseData = new MouseRobot(WIDTH,HEIGHT,pane);
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
								      .setPos(new Vector(22,12))
								      .setDir(new Vector(-1,0))
								      .setPlane(new Vector(0,0.66));
		
		static {
			ElementTable table = new ElementTable();
			table.add(new Element(1,"border",Color.ORANGE,false));
			table.add(new Element(2,"green",Color.GREEN,true));
			table.add(new Element(3,"red",Color.RED,true));
			table.add(new Element(4,"magenta",Color.MAGENTA,true));
			table.add(new Element(5,"gold",Color.YELLOW,false));
			
			int[][] map = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			    {1,0,0,0,0,0,2,2,2,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
				{1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,3,0,0,0,3,0,0,0,1},
				{1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,2,2,0,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,0,0,0,0,5,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,0,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			};
			
			mainMap = new WorldMap(map,table);
		}
	}
}
