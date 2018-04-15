package com.dezzy.skorp3.skorp3D.raycast.actions;

import java.awt.event.ActionEvent;

import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;

public class MoveForwardAction extends MoveAction {
	private static final long serialVersionUID = -2046723359133435076L;
	
	public MoveForwardAction(WorldMap _map, Camera _camera) {
		super(_map, _camera);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		camera.moveForward(map,moveFactor);		
	}

}
