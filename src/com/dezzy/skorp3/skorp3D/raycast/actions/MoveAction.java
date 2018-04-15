package com.dezzy.skorp3.skorp3D.raycast.actions;

import javax.swing.AbstractAction;

import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;

@SuppressWarnings("unused")
public abstract class MoveAction extends AbstractAction {
	private static final long serialVersionUID = 9209465263115949897L;
	
	protected Camera camera;
	protected WorldMap map;
	protected double moveFactor = 1;
	
	public MoveAction(WorldMap _map, Camera _camera) {
		map = _map;
		camera = _camera;
	}
	
	public void setFactor(double factor) {
		moveFactor = factor;
	}
	
	public void setWorldMap(WorldMap _map) {
		map = _map;
	}
}
