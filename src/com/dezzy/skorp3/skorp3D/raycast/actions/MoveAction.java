package com.dezzy.skorp3.skorp3D.raycast.actions;

import javax.swing.AbstractAction;

import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;

@SuppressWarnings("unused")
public abstract class MoveAction extends AbstractAction {
	private static final long serialVersionUID = 9209465263115949897L;
	
	protected RaycastGraphicsContainer container;
	protected double moveFactor = 1;
	
	public MoveAction(RaycastGraphicsContainer _container) {
		container = _container;
	}
	
	public void setFactor(double factor) {
		moveFactor = factor;
	}
	
	public void setWorldMap(WorldMap _map) {
		container.map = _map;
	}
}
