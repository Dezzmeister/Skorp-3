package com.dezzy.skorp3.skorp3D.raycast2.core;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.RaycastContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;

public class RaycastContainer2 implements RaycastContainer {
	public volatile Graphics g;
	public volatile RaycastMap map;
	public volatile Mouse mouse;
	public volatile JPanel panel;
	public volatile Camera camera;
	public volatile Container pane;
	public volatile boolean[] keys;
	
	public RaycastContainer2(RaycastMap _map, MouseData _mouseData, JPanel _panel, Container _pane) {
		map = _map;
		mouse = _mouseData;
		panel = _panel;
		pane = _pane;
	}
	
	public RaycastContainer2() {
		Logger.warn("RaycastGraphicsContainer created with default constructor! This could cause null reference problems!");
	}
	
	public RaycastContainer2 setGraphics(Graphics graphics) {
		g = graphics;
		return this;
	}
	
	public RaycastContainer2 setPanel(JPanel _panel) {
		panel = _panel;
		return this;
	}
	
	public RaycastContainer2 setWorldMap(RaycastMap _map) {
		map = _map;
		return this;
	}
	
	public RaycastContainer2 setMouseData(Mouse _mouse) {
		mouse = _mouse;
		return this;
	}
	
	public RaycastContainer2 setCamera(Camera _camera) {
		camera = _camera;
		return this;
	}
	
	public RaycastContainer2 setContainer(Container _pane) {
		pane = _pane;
		return this;
	}
	
	public RaycastContainer2 setKeys(boolean[] _keys) {
		keys = _keys;
		return this;
	}
	
	public boolean hasUpdated() {
		return mouse.hasUpdated();
	}
}
