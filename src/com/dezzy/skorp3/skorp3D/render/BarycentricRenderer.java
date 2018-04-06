package com.dezzy.skorp3.skorp3D.render;

import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

public class BarycentricRenderer implements Renderer {
	private GraphicsContainer container;
	
	public BarycentricRenderer(GraphicsContainer _container) {
		container = _container;
	}
	
	@Override
	public synchronized void render() {
		Triangle[] triangles = container.collapse();
		System.out.println(triangles[0]);
	}

	@Override
	public synchronized boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public synchronized GraphicsContainer getGraphicsContainer() {
		return container;
	}

}
