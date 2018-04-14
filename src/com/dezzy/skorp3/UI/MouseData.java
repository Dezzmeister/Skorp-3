package com.dezzy.skorp3.UI;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pure data class, holds information about mouse coordinates obtained from a <code>SkorpPanel</code>. 
 * Used in 3D rendering.
 * 
 * @author Dezzmeister
 *
 */
public class MouseData implements Mouse {
	private AtomicInteger x = new AtomicInteger(0);
	private AtomicInteger y = new AtomicInteger(0);
	
	private AtomicInteger px = new AtomicInteger(0);
	private AtomicInteger py = new AtomicInteger(0);
	
	private volatile int width;
	private volatile int height;
	
	private boolean updated = true;
	
	public MouseData(int _width, int _height) {
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
	
	public int px() {
		return px.get();
	}
	
	public int py() {
		return py.get();
	}
	
	public int dx() {
		return x()-px();
	}
	
	public int dy() {
		return y()-py();
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
