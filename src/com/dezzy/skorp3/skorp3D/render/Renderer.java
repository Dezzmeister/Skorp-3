package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;

public interface Renderer extends Runnable {
	/**
	 * Code that will draw to a Graphics object in a GraphicsContainer.
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
	 * Should assess inputs and return true if a frame needs to be redrawn.
	 * 
	 * @return true if frame should be redrawn
	 */
	public boolean shouldRedraw();
	
	public GraphicsContainer getGraphicsContainer();
}
