package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;

public class RaycastGraphicsContainer {
	public volatile Graphics g;
	public volatile WorldMap map;
	public volatile Mouse mouse;
	public volatile JPanel panel;
	public volatile Camera camera;
	public volatile Container pane;
	public volatile boolean[] keys;
	
	public RaycastGraphicsContainer(WorldMap _map, MouseData _mouseData, JPanel _panel, Container _pane) {
		map = _map;
		mouse = _mouseData;
		panel = _panel;
		pane = _pane;
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
	
	public RaycastGraphicsContainer setMouseData(Mouse _mouse) {
		mouse = _mouse;
		return this;
	}
	
	public RaycastGraphicsContainer setCamera(Camera _camera) {
		camera = _camera;
		return this;
	}
	
	public RaycastGraphicsContainer setContainer(Container _pane) {
		pane = _pane;
		return this;
	}
	
	public RaycastGraphicsContainer setKeys(boolean[] _keys) {
		keys = _keys;
		return this;
	}
	
	public boolean hasUpdated() {
		return mouse.hasUpdated();
	}
}
