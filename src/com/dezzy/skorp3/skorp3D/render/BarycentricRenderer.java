package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;

public class BarycentricRenderer implements Renderer {
	private GraphicsContainer container;
	
	public BarycentricRenderer(GraphicsContainer _container) {
		container = _container;
	}
	
	@Override
	public void render() {
		
	}

	@Override
	public boolean shouldRedraw() {
		
		return false;
	}

	@Override
	public GraphicsContainer getGraphicsContainer() {
		return container;
	}

}
