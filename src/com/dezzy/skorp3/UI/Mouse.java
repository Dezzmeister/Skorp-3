package com.dezzy.skorp3.UI;

import com.dezzy.skorp3.game3D.Updateable;

/**
 * Represents a Mouse object or an object that is acted upon by a mouse listener and holds data about a mouse.
 * 
 * @author Dezzmeister
 *
 */
public interface Mouse extends Updateable {
	/**
	 * Returns the current X position of the mouse.
	 * 
	 * @return current X
	 */
	public int x();
	
	/**
	 * Returns the current Y position of the mouse.
	 * 
	 * @return current Y
	 */
	public int y();
	
	/**
	 * Returns the previous x position of the mouse.
	 * 
	 * @return previous x
	 */
	public int px();
	
	/**
	 * Returns the previous y position of the mouse.
	 * 
	 * @return previous y
	 */
	public int py();
	
	/**
	 * Returns the change in mouse X position since the last movement.
	 * 
	 * @return delta X
	 */
	public int dx();
	
	/**
	 * Returns the change in mouse Y position since the last movement.
	 * 
	 * @return delta Y
	 */
	public int dy();
	
	/**
	 * Returns the width of the space available to the mouse.
	 * 
	 * @return width
	 */
	public int width();
	
	/**
	 * Returns the height of the space available to the mouse.
	 * 
	 * @return height
	 */
	public int height();
	
	/**
	 * Sets the X position of the mouse. Used in SkorpPanel and should only
	 * be called by a MouseMotionListener.
	 * 
	 * @param x new X position of the mouse
	 */
	public void x(int x);
	
	/**
	 * Sets the Y position of the mouse. Used in SkorpPanel and should only
	 * be called by a MouseMotionListener.
	 * 
	 * @param y new Y position of the mouse
	 */
	public void y(int y);
}
