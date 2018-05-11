package com.dezzy.skorp3.skorp3D.raycast.render;

import static com.dezzy.skorp3.skorp3D.raycast2.core.RenderUtils.*;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast2.core.Linetype;
import com.dezzy.skorp3.skorp3D.raycast2.core.Portal;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;

public class Camera {
	public Vector2 pos;
	public Vector2 dir;
	public Vector2 plane;
	private Vector2 sidedir;
	
	private float moveSpeed = 0.055f;
	private float rotSpeed = 0.005f;
	
	public double fogDistance = 10;
	
	public Camera setPos(Vector2 _pos) {
		pos = _pos;
		return this;
	}
	
	public Camera setDir(Vector2 _dir) {
		dir = _dir;
		computeSideDir();
		return this;
	}
	
	public Camera setPlane(Vector2 _plane) {
		plane = _plane;
		return this;
	}
	
	public double getRotationSpeed() {
		return rotSpeed;
	}
	
	public void setRotationSpeed(float speed) {
		rotSpeed = speed;
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	public void setMoveSpeed(float speed) {
		moveSpeed = speed;
	}
	
	private void computeSideDir() {
		float t = (float) (Math.PI/2.0);
		
		float x = (float) (dir.x*Math.cos(t) - dir.y*Math.sin(t));
		float y = (float) (dir.x*Math.sin(t) + dir.y*Math.cos(t));
		
		sidedir = new Vector2(x,y);
	}
	
	/**
	 * Rotates the camera left by a factor of the camera's internal rotation speed.
	 * 
	 * @param factor multiplied by the rotation speed, product used to rotate left
	 */
	public void rotateLeft(float factor) {
		float speed = factor * this.rotSpeed;
		
		float oldDirX = dir.x;
		float cr = (float) Math.cos(speed);
		float sr = (float) Math.sin(speed);
		dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    float oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
	
	/**
	 * Rotates the camera right by a factor of the camera's internal rotation speed.
	 * 
	 * @param factor multiplied by the rotation speed, product used to rotate right
	 */
	public void rotateRight(float factor) {
		float speed = factor * this.rotSpeed;
		
		float oldDirX = dir.x;
		float cr = (float) Math.cos(-speed);
		float sr = (float) Math.sin(-speed);
	    dir.x = dir.x * cr - dir.y * sr;
	    dir.y = oldDirX * sr + dir.y * cr;
	    float oldPlaneX = plane.x;
	    plane.x = plane.x * cr - plane.y * sr;
	    plane.y = oldPlaneX * sr + plane.y * cr;
	}
	
	public void moveForward(WorldMap map, float factor) {
		float speed = factor * moveSpeed;
		
		if (map.get((int)(pos.x + dir.x * speed), (int)pos.y).id() == 0) {
			pos.x += dir.x * speed;
		}
		if (map.get((int)pos.x,(int)(pos.y + dir.y * speed)).id() == 0) {
			pos.y += dir.y * speed;
		}
	}
	
	public void moveBackward(WorldMap map, float factor) {
		float speed = factor * moveSpeed;
		
		if (map.get((int)(pos.x - dir.x * speed),(int)pos.y).id() == 0) {
			pos.x -= dir.x * speed;
		}
	    if (map.get((int)pos.x,(int)(pos.y - dir.y * speed)).id() == 0) {
	    	pos.y -= dir.y * speed;
	    }
	}
	
	public void moveLeft(WorldMap map, float factor) {
		
	}
	
	public void moveForward(float factor) {
		float speed = factor * moveSpeed;
		
		pos.x+=dir.x*speed;
		pos.y+=dir.y*speed;
	}
	
	public void moveBackward(float factor) {
		float speed = factor * moveSpeed;
		
		pos.x-=dir.x*speed;
		pos.y-=dir.y*speed;
	}
	
	public void moveLeft(float factor) {
		computeSideDir();
		float speed = factor * moveSpeed;
		
		pos.x+=sidedir.x*speed;
		pos.y+=sidedir.y*speed;
	}
	
	public void moveRight(float factor) {
		computeSideDir();
		float speed = factor * moveSpeed;
		
		pos.x-=sidedir.x*speed;
		pos.y-=sidedir.y*speed;
	}
}
