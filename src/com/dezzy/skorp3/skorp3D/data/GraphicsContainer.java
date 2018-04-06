package com.dezzy.skorp3.skorp3D.data;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.log.Logger;

public class GraphicsContainer {
	public Graphics g;
	public MouseData mouse;
	public Data3D data3D;
	public VBOList vboList;
	public JPanel panel;
	
	public GraphicsContainer(Graphics _g, MouseData _mouse, Data3D _data, VBOList _vboList, JPanel _panel) {
		g = _g;
		mouse = _mouse;
		data3D = _data;
		vboList = _vboList;
		panel = _panel;
	}
	
	public GraphicsContainer() {
		Logger.warn("Creating a GraphicsContainer with default constructor! This could create null references.");
	}
	
	public GraphicsContainer setGraphics(Graphics _g) {
		g = _g;
		return this;
	}
	
	public GraphicsContainer setMouseData(MouseData _mouse) {
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
}
