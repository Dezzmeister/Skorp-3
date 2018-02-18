package com.dezzy.skorp3.field;

import java.util.function.BiConsumer;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game.Shape;

/**
 * Entity should be a superclass for any game component that appears on the field. Entities can collide with each other
 * by means of Shape.
 * 
 * @author Dezzmeister
 * @see Shape
 *
 */
public abstract class Entity {
	protected Shape shape;
	public Pair<Double> point;
	
	private BiConsumer<Entity,Entity> collisionMethod;
	
	{
		point = new Pair<Double>(0.0,0.0);
	}
	
	protected Entity() {
		
	}
	
	public Entity(Shape _shape) {
		shape = _shape;
	}
	
	public Entity(double x, double y) {
		point = new Pair<Double>(x,y);
	}

	public Shape getShape() {
		return shape;
	}
	
	public void placeAt(double _x, double _y) {
		point.x = _x;
		point.y = _y;
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
