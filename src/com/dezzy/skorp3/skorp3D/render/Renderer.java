package com.dezzy.skorp3.skorp3D.render;

import java.awt.Graphics;

/**
 * Details the requirements of a class that can be used to render a scene.
 * 
 * @author Dezzmeister
 *
 */
public interface Renderer extends Runnable {
	/**
	 * Code that will draw to a Graphics object.
	 */
	public void render();
	
	/**
	 * DO NOT override this method. This method exists to allow the renderer to run in
	 * a separate thread, and it has been defined to call render() when shouldRedraw() returns true.
	 */
	public default void run() {
		while (true) {
			if (shouldRedraw()) {
				render();
			}
		}
	}
	
	/**
	 * Assesses inputs and return true if a frame needs to be redrawn.
	 * 
	 * @return true if frame should be redrawn
	 */
	public boolean shouldRedraw();
	
	/**
	 * Updates the graphics object belonging to this Renderer. Used with JPanel's
	 * <code>paintComponent()</code>.
	 * 
	 * @param g AWT Graphics object
	 */
	public void updateGraphicsObject(Graphics g);
}
