package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;

public class RaycastGraphicsContainer {
	public volatile Graphics g;
	public volatile WorldMap map;
	public volatile MouseData mouse;
	public volatile JPanel panel;
	
	public RaycastGraphicsContainer(WorldMap _map, MouseData _mouseData, JPanel _panel) {
		map = _map;
		mouse = _mouseData;
		panel = _panel;
	}
	
	public RaycastGraphicsContainer() {
		Logger.warn("RaycastGraphicsContainer created with default constructor! This could cause null reference problems!");
	}
	
	public RaycastGraphicsContainer setGraphics(Graphics graphics) {
		g = graphics;
		return this;
	}
	
	public RaycastGraphicsContainer setPanel(JPanel _panel) {
		panel = _panel;
		return this;
	}
	
	public RaycastGraphicsContainer setWorldMap(WorldMap _map) {
		map = _map;
		return this;
	}
	
	public RaycastGraphicsContainer setMouseData(MouseData _mouse) {
		mouse = _mouse;
		return this;
	}
	
	public boolean hasUpdated() {
		return mouse.hasUpdated();
	}
}
