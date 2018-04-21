package com.dezzy.skorp3.skorp3D.raycast2;

import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;

public class Raycaster2 implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	private final int WIDTH;
	private final int HEIGHT;
	
	public Raycaster2(RaycastGraphicsContainer _container, int _width, int _height) {
		container = _container;
		WIDTH = _width;
		HEIGHT = _height;
	}
	
	
	@Override
	public void render() {
		
	}

	@Override
	public boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public RaycastGraphicsContainer getGraphicsContainer() {
		return container;
	}

}
