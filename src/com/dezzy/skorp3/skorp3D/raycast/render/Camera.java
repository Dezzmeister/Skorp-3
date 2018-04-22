package com.dezzy.skorp3.skorp3D.raycast.render;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;

public class Camera {
	public Vector pos;
	public Vector dir;
	public Vector plane;
	private Vector sidedir;
	
	private double moveSpeed = 0.055;
	private double rotSpeed = 0.005;
	
	public double fogDistance = 10;
	
	public Camera setPos(Vector _pos) {
		pos = _pos;
		return this;
	}
	
	public Camera setDir(Vector _dir) {
		dir = _dir;
		computeSideDir();
		return this;
	}
	
	public Camera setPlane(Vector _plane) {
		plane = _plane;
		return this;
	}
	
	public double getRotationSpeed() {
		return rotSpeed;
	}
	
	public void setRotationSpeed(double speed) {
		rotSpeed = speed;
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	public void setMoveSpeed(double speed) {
		moveSpeed = speed;
	}
	
	private void computeSideDir() {
		double t = Math.PI/2.0;
		
		double x = dir.x*Math.cos(t) - dir.y*Math.sin(t);
		double y = dir.x*Math.sin(t) + dir.y*Math.cos(t);
		
		sidedir = new Vector(x,y);
	}
	
	/**
	 * Rotates the camera left by a factor of the camera's internal rotation speed.
	 * 
	 * @param factor multiplied by the rotation speed, product used to rotate left
	 */
	public void rotateLeft(double factor) {
		double speed = factor * this.rotSpeed;
		
		double oldDirX = dir.x;
		double cr = Math.cos(speed);
		double sr = Math.sin(speed);
		dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    double oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
	
	/**
	 * Rotates the camera right by a factor of the camera's internal rotation speed.
	 * 
	 * @param factor multiplied by the rotation speed, product used to rotate right
	 */
	public void rotateRight(double factor) {
		double speed = factor * this.rotSpeed;
		
		double oldDirX = dir.x;
	    double cr = Math.cos(-speed);
	    double sr = Math.sin(-speed);
	    dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    double oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
	
	public void moveForward(WorldMap map, double factor) {
		double speed = factor * moveSpeed;
		
		if (map.get((int)(pos.x + dir.x * speed), (int)pos.y).id() == 0) {
			pos.x += dir.x * speed;
		}
		if (map.get((int)pos.x,(int)(pos.y + dir.y * speed)).id() == 0) {
			pos.y += dir.y * speed;
		}
	}
	
	public void moveBackward(WorldMap map, double factor) {
		double speed = factor * moveSpeed;
		
		if (map.get((int)(pos.x - dir.x * speed),(int)pos.y).id() == 0) {
			pos.x -= dir.x * speed;
		}
	    if (map.get((int)pos.x,(int)(pos.y - dir.y * speed)).id() == 0) {
	    	pos.y -= dir.y * speed;
	    }
	}
	
	public void moveLeft(WorldMap map, double factor) {
		
	}
	
	public void moveForward(double factor) {
		double speed = factor * moveSpeed;
		
		pos.x+=dir.x*speed;
		pos.y+=dir.y*speed;
	}
	
	public void moveBackward(double factor) {
		double speed = factor * moveSpeed;
		
		pos.x-=dir.x*speed;
		pos.y-=dir.y*speed;
	}
	
	public void moveLeft(double factor) {
		computeSideDir();
		double speed = factor * moveSpeed;
		
		pos.x+=sidedir.x*speed;
		pos.y+=sidedir.y*speed;
	}
	
	public void moveRight(double factor) {
		computeSideDir();
		double speed = factor * moveSpeed;
		
		pos.x-=sidedir.x*speed;
		pos.y-=sidedir.y*speed;
	}
}
