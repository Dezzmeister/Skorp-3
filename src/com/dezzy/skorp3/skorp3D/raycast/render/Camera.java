package com.dezzy.skorp3.skorp3D.raycast.render;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;

public class Camera {
	public Vector pos;
	public Vector dir;
	public Vector plane;
	
	public Camera setPos(Vector _pos) {
		pos = _pos;
		return this;
	}
	
	public Camera setDir(Vector _dir) {
		dir = _dir;
		return this;
	}
	
	public Camera setPlane(Vector _plane) {
		plane = _plane;
		return this;
	}
	
	public void rotateLeft(double rotSpeed) {
		double oldDirX = dir.x;
		double cr = Math.cos(rotSpeed);
		double sr = Math.sin(rotSpeed);
		dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    double oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
	
	public void rotateRight(double rotSpeed) {
		double oldDirX = dir.x;
	    double cr = Math.cos(-rotSpeed);
	    double sr = Math.sin(-rotSpeed);
	    dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    double oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
}
