package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;
import com.dezzy.skorp3.skorp3D.render.gpu.GPURaycaster;

public class Renderers {
	
	public static final TrueRenderer createAndStartBarycentricRenderer(GraphicsContainer container) {
		BarycentricRenderer renderer = new BarycentricRenderer(container);
		return renderer;
	}
	
	public static final RaycastRenderer createAndStartRaycaster(RaycastGraphicsContainer container, int width, int height) {
		RaycastRenderer renderer = new GPURaycaster(container, width, height);
		return renderer;
	}
}
