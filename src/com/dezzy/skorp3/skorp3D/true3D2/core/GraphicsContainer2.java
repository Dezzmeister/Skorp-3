package com.dezzy.skorp3.skorp3D.true3D2.core;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.log.Logger;

public class GraphicsContainer2 {
	public volatile Graphics g;
	public volatile Mouse mouse;
	public Data3D data3D;
	public JPanel panel;

	public GraphicsContainer2(Graphics _g, Mouse _mouse, Data3D _data, JPanel _panel) {
		g = _g;
		mouse = _mouse;
		data3D = _data;
		panel = _panel;
	}
	
	public GraphicsContainer2() {
		Logger.warn("Creating a GraphicsContainer2 with default constructor! This could create null references.");
	}
	
	public synchronized GraphicsContainer2 setGraphics(Graphics _g) {
		g = _g;
		return this;
	}
	
	public synchronized GraphicsContainer2 setMouseData(Mouse _mouse) {
		mouse = _mouse;
		return this;
	}
	
	public GraphicsContainer2 setData3D(Data3D _data) {
		data3D = _data;
		return this;
	}
	
	public GraphicsContainer2 setPanel(JPanel _panel) {
		panel = _panel;
		return this;
	}

	public boolean hasUpdated() {
		return mouse.hasUpdated();
	}
}
