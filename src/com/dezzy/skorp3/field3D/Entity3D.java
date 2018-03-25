package com.dezzy.skorp3.field3D;

import java.awt.Color;
import java.util.List;
import java.util.function.BiConsumer;

import com.dezzy.skorp3.field.Sendable;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game.Shape;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Vertex;

/**
 * Entity3D should be a superclass for any 3D game component that appears on the field. Entities can collide with each other
 * by means of Shape3D.
 * Entity3D extends Transformable because Transformable provides Matrix operations. Since all Entity3D's can appear as objects on
 * the field, they should be Transformable.
 * Entity3D implements Sendable to ensure that all Entity3D's can be encoded and sent through TCP or UDP for decoding on
 * another computer. This ensures that multiplayer will work with Entity3D's.
 * 
 * @author Dezzmeister
 * @see Shape
 *
 */
public abstract class Entity3D extends Transformable implements Sendable {	
	protected Shape3D shape;
	protected Vertex point;
	protected Color color;
	public boolean updated = false;
	
	private BiConsumer<Entity3D,Entity3D> collisionMethod;
	
	{
		point = new Vertex(0.0,0.0,0.0);
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
		point = new Vertex(x,y,z);
	}

	public Shape3D getShape() {
		return shape;
	}
	
	public double x() {
		return point.x;
	}
	
	public double y() {
		return point.y;
	}
	
	public double z() {
		return point.z;
	}
	
	public Color color() {
		return color;
	}
	
	public void x(double x) {
		point.x = x;
		update();
	}
	
	public void y(double y) {
		point.y = y;
		update();
	}
	
	public void z(double z) {
		point.z = z;
		update();
	}
	
	public void color(Color _color) {
		color = _color;
		update();
	}
	
	public Vertex point() {
		return point;
	}
	
	protected void update() {
		updated = true;
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
	 * Decompose an Entity3D into its triangles.
	 * 
	 * @return
	 */
	public abstract List<Triangle> decompose();
}

