package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaytraceGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaytraceRenderer;
import com.dezzy.skorp3.skorp3D.raycast.render.Raytracer;

public final class Renderers {
	
	private Renderers() {
		
	}
	
	public static final TrueRenderer createAndStartBarycentricRenderer(GraphicsContainer container) {
		BarycentricRenderer renderer = new BarycentricRenderer(container);
		return renderer;
	}
	
	public static final RaytraceRenderer createAndStartRaytracer(RaytraceGraphicsContainer container) {
		Raytracer renderer = new Raytracer(container);
		return renderer;
	}
}
