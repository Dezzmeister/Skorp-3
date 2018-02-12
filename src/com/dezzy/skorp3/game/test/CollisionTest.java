package com.dezzy.skorp3.game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.game.CollisionHandler;

public class CollisionTest {
	
	CollisionHandler col = new CollisionHandler();
	Entity circle1 = new Entity(100,200,40,40);
	Entity rect1 = new Entity(300,200,100,200);
	
	protected void testCircleOnCircle() {
		
	}
	
	protected void testRectOnRect() {
		
	}
	/*
	 * All passed
	@Test
	public void testRectOnCircle() {
		assertFalse(col.rectangleHitCircle(circle1, rect1));
		rect1.placeAt(100, 200);
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.y = 300;
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.x = 200;
		assertFalse(col.rectangleHitCircle(circle1, rect1));
		circle1.placeAt(250,200);
		rect1.placeAt(300, 200);
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.y = 100;
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.x = 350;
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.y = 200;
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.x = 369;
		assertTrue(col.rectangleHitCircle(circle1, rect1));
		circle1.placeAt(364, 314);
		assertTrue(col.rectangleHitCircle(circle1, rect1));
	}*/
}
