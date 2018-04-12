package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;

/**
 * A True 3D Renderer - renders a true 3D scene using a GraphicsContainer.
 * 
 * @author Dezzmeister
 *
 */
public interface TrueRenderer extends Renderer {
	
	public GraphicsContainer getGraphicsContainer();
}
