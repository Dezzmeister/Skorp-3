package com.dezzy.skorp3.UI;

import java.awt.Container;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

public class MouseRobot implements Mouse {
	private AtomicInteger x = new AtomicInteger(0);
	private AtomicInteger y = new AtomicInteger(0);
	
	private AtomicInteger px = new AtomicInteger(0);
	private AtomicInteger py = new AtomicInteger(0);
	private Robot robot;
	private Container pane;
	
	private volatile int width;
	private volatile int height;
	
	private boolean updated = true;
	
	public MouseRobot(Container _pane) {
		pane = _pane;
	}
	
	public MouseRobot(int _width, int _height, Container _pane) {
		width = _width;
		height = _height;
		pane = _pane;
	}
	
	{
		try {
			robot = new Robot();
			robot.setAutoDelay(0);
		} catch (Exception e) {
			
		}
	}
	
	public MouseRobot(int _width, int _height) {
		width = _width;
		height = _height;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public int x() {
		return x.get();
	}
	
	public int y() {
		return y.get();
	}
	
	public void x(int _x) {
		if (x.get() != _x) update();
		px.set(x());
		x.set(_x);
	}
	
	public void y(int _y) {
		if (y.get() != _y) update();
		py.set(y());
		y.set(_y);
	}
	
	@SuppressWarnings("unused")
	private void forceToCenterX() {
		robot.mouseMove(width/2, y());
	}
	
	@SuppressWarnings("unused")
	private void forceToCenterY() {
		robot.mouseMove(x(), height/2);
	}
	
	private void forceToCenter() {
		Point p = new Point(width/2,height/2);
		SwingUtilities.convertPointToScreen(p, pane);
		robot.mouseMove(p.x,p.y);
	}
	
	public void setWidth(int _width) {
		width = _width;
	}
	
	public void setHeight(int _height) {
		height = _height;
	}
	
	public int px() {
		return px.get();
	}
	
	public int py() {
		return py.get();
	}
	
	public int dx() {
		int ret = x() - (width/2);
		forceToCenter();
		return ret;
	}
	
	public int dy() {
		int ret = y() - (height/2);
		forceToCenter();
		return ret;
	}

	@Override
	public void update() {
		updated = true;		
	}

	@Override
	public boolean hasUpdated() {
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}
}
