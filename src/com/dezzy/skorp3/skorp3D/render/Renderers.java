package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;

public class Renderers {
	
	public static final TrueRenderer createAndStartBarycentricRenderer(GraphicsContainer container) {
		BarycentricRenderer renderer = new BarycentricRenderer(container);
		return renderer;
	}
}
