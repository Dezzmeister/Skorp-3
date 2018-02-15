package com.dezzy.skorp3.game;

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
 * @see CollisionHandler
 * @author Dezzmeister
 *
 */
public enum Shape {
	LINE,
	RECTANGLE, //Axis aligned rectangle
	CIRCLE
}
