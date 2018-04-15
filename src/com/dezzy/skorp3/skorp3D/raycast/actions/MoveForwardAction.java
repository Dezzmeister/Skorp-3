package com.dezzy.skorp3.skorp3D.raycast.actions;

import java.awt.event.ActionEvent;

import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;

public class MoveForwardAction extends MoveAction {
	private static final long serialVersionUID = -2046723359133435076L;
	
	public MoveForwardAction(RaycastGraphicsContainer container) {
		super(container);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		container.camera.moveForward(container.map,moveFactor);
		container.panel.repaint();
	}

}
