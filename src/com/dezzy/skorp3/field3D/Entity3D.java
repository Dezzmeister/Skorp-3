package com.dezzy.skorp3.field3D;

import java.awt.Color;
import java.util.List;
import java.util.function.BiConsumer;

import com.dezzy.skorp3.field.Sendable;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game.Shape;
import com.dezzy.skorp3.game.Triple;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.math3D.Vertex;

/**
 * Entity should be a superclass for any game component that appears on the field. Entities can collide with each other
 * by means of Shape.
 * 
 * @author Dezzmeister
 * @see Shape
 *
 */
public abstract class Entity3D implements Sendable {	
	protected Shape3D shape;
	public Triple<Double> point;
	public Color color;
	
	private BiConsumer<Entity3D,Entity3D> collisionMethod;
	
	{
		point = new Triple<Double>(0.0,0.0,0.0);
	}
	
	/**
	 * Protected constructors. Entity is abstract and the only instances should be instances of subclasses.
	 */
	protected Entity3D() {
		
	}
	
	protected Entity3D(Shape3D _shape) {
		shape = _shape;
	}
	
	protected Entity3D(double x, double y, double z) {
		point = new Triple<Double>(x,y,z);
	}

	public Shape3D getShape() {
		return shape;
	}
	
	public void placeAt(double _x, double _y, double _z) {
		point.x = _x;
		point.y = _y;
		point.z = _z;
	}
	
	/**
	 * This method is part of the fluent interface and allows for method chaining.
	 * This should be used on construction.
	 * 
	 * @param vbo VBO to add this to
	 * @return this (for method chaining)
	 */
	public Entity3D addto(VBO3D vbo) {
		vbo.add(this);
		return this;
	}
	
	/**
	 * This method accepts a BiConsumer<Entity,Entity> defining what happens when this Entity collides with another.
	 * The first Entity parameter when accept() is called will be "this", and the second parameter will be the other Entity being tested for collision.
	 * 
	 * @param _collisionMethod BiConsumer<Entity,Entity>
	 */
	public void setCollisionAction(BiConsumer<Entity3D,Entity3D> _collisionMethod) {
		collisionMethod = _collisionMethod;
	}
	
	public void onCollisionWith(Entity3D entity) {
		if (Physics.hasCollided(this,entity) && collisionMethod != null) {
			collisionMethod.accept(this, entity);
		}
	}
	
	/**
	 * Decompose an Entity3D into its vertices.
	 * 
	 * @return
	 */
	public abstract List<Vertex> decompose();
}

