package com.dezzy.skorp3.field;

import java.util.function.BiConsumer;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game.Shape;

/**
 * Entity should be a superclass for any game component that appears on the field. Entities can collide with each other
 * by means of Shape.
 * 
 * @see Shape
 * @author Dezzmeister
 *
 */
public abstract class Entity {
	protected Shape shape;
	public Pair point;
	public int width;
	public int height;
	
	//An extra coordinate pair, to be used where necessary
	public Pair endpoint;
	
	private BiConsumer<Entity,Entity> collisionMethod;
	
	protected Entity() {
		point = new Pair(0,0);
		endpoint = new Pair(0,0);
	}
	
	public Entity(int x, int y, int width, int height) {
		initGeometry(x,y,width,height);
	}
	
	public Entity(int x, int y, int width, int height, Shape _shape) {
		initGeometry(x,y,width,height);
		shape = _shape;
	}
	
	private void initGeometry(int _x, int _y, int _width, int _height) {
		point = new Pair(_x,_y);
		width = _width;
		height = _height;
	}
	
	public void resize(int _width, int _height) {
		width = _width;
		height = _height;
	}
	
	public void placeAt(int _x, int _y) {
		point.x = _x;
		point.y = _y;
	}
	
	public void placeEndAt(int x, int y) {
		endpoint.x = x;
		endpoint.y = y;
	}
	
	public void placeAt(int x, int y, int x2, int y2) {
		placeAt(x,y);
		placeEndAt(x2,y2);
	}

	public Shape getShape() {
		return shape;
	}
	
	/**
	 * This method accepts a BiConsumer<Entity,Entity> defining what happens when this Entity collides with another.
	 * The first Entity parameter when accept() is called will be "this", and the second parameter will be the other Entity being tested for collision.
	 * 
	 * @param _collisionMethod BiConsumer<Entity,Entity>
	 */
	public void setCollisionAction(BiConsumer<Entity,Entity> _collisionMethod) {
		collisionMethod = _collisionMethod;
	}
	
	public void onCollisionWith(Entity entity) {
		if (Physics.hasCollided(this,entity) && collisionMethod != null) {
			collisionMethod.accept(this, entity);
		}
	}
}
