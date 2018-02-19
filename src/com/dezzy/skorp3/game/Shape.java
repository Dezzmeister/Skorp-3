package com.dezzy.skorp3.game;

import com.dezzy.skorp3.field.Entity;

/**
 * Shape is used to determine how collision handling will work. Every Entity must have a shape
 * because as members of the field package, entities will be playing field components and should be able to interact
 * with other components.
 * 
 * If you want to add a new type of shape like a TRIANGLE, you have to edit CollisionHandler and write methods to check for collisions
 * between other shapes and your TRIANGLE.
 * 
 * In the future, Shape may also be used to determine other behaviors.
 * 
 * @author Dezzmeister
 * @see CollisionHandler
 *
 */
public enum Shape {
	//TODO we need an ELLIPSE and also maybe a QUAD or something (LSP)
	POINT,
	LINE,
	RECTANGLE, //Axis aligned rectangle
	CIRCLE;
	
	/**
	 * Orders a pair of entities by their shape values and returns an array of Entities like 
	 * [lowest,highest].
	 * 
	 * @param e1 first Entity
	 * @param e2 second Entity
	 * @return a pair of Entities ordered by Shape value
	 */
	public static Entity[] orderByShape(Entity e1, Entity e2) {
		Entity first = (e1.getShape().ordinal() < e2.getShape().ordinal()) ? e1 : e2;
		Entity second = (first==e2) ? e1 : e2;
		
		return new Entity[]{first,second};
	}
}
