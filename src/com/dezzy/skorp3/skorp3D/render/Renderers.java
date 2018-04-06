package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;

public class Renderers {
	
	public static final Renderer createAndStartBarycentricRenderer(GraphicsContainer container) {
		BarycentricRenderer renderer = new BarycentricRenderer(container);
		Thread renderThread = new Thread(renderer,"Skorp 3 Renderer");
		renderThread.start();
		return renderer;
	}
}
