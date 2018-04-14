package com.dezzy.skorp3.skorp3D.data;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.math3D.datastructures.Collapsable;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

public class GraphicsContainer implements Collapsable<Triangle[]> {
	public volatile Graphics g;
	public volatile Mouse mouse;
	public Data3D data3D;
	public VBOList vboList;
	public JPanel panel;

	public GraphicsContainer(Graphics _g, Mouse _mouse, Data3D _data, VBOList _vboList, JPanel _panel) {
		g = _g;
		mouse = _mouse;
		data3D = _data;
		vboList = _vboList;
		panel = _panel;
	}
	
	public GraphicsContainer() {
		Logger.warn("Creating a GraphicsContainer with default constructor! This could create null references.");
	}
	
	public synchronized GraphicsContainer setGraphics(Graphics _g) {
		g = _g;
		return this;
	}
	
	public synchronized GraphicsContainer setMouseData(Mouse _mouse) {
		mouse = _mouse;
		return this;
	}
	
	public GraphicsContainer setData3D(Data3D _data) {
		data3D = _data;
		return this;
	}
	
	public GraphicsContainer setVBOList(VBOList _vboList) {
		vboList = _vboList;
		return this;
	}
	
	public GraphicsContainer setPanel(JPanel _panel) {
		panel = _panel;
		return this;
	}

	public boolean hasUpdated() {
		return mouse.hasUpdated() ||
			   vboList.hasUpdated();
	}
	
	@Override
	public synchronized Triangle[] collapse() {
		return vboList.collapse().collapse();
	}
}
