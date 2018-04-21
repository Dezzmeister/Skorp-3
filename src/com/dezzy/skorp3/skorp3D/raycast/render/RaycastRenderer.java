package com.dezzy.skorp3.skorp3D.raycast.render;

import com.dezzy.skorp3.skorp3D.raycast.core.RaycastContainer;
import com.dezzy.skorp3.skorp3D.render.Renderer;

public interface RaycastRenderer extends Renderer {
	public RaycastContainer getGraphicsContainer();
}
