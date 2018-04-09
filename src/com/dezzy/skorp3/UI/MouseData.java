package com.dezzy.skorp3.UI;

import java.util.concurrent.atomic.AtomicInteger;

import com.dezzy.skorp3.game3D.Updateable;

/**
 * Pure data class, holds information about mouse coordinates obtained from a <code>SkorpPanel</code>. 
 * Used in 3D rendering.
 * 
 * @author Dezzmeister
 *
 */
public class MouseData implements Updateable {
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
	
	/**
	 * Returns the previous x position of the mouse.
	 * 
	 * @return previous x
	 */
	public int px() {
		return px.get();
	}
	
	/**
	 * Returns the previous y position of the mouse.
	 * 
	 * @return previous y
	 */
	public int py() {
		return py.get();
	}
	
	/**
	 * Returns the change in mouse X position since the last movement.
	 * 
	 * @return delta X
	 */
	public int dx() {
		return x()-px();
	}
	
	/**
	 * The change in mouse Y position since the last movement.
	 * 
	 * @return delta Y
	 */
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
