package com.dezzy.skorp3.game;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.field.Obstacle;

/**
 * Unit test for CollisionHandler.
 * 
 * Try putting some of these shapes in Processing to verify that they have actually collided.
 * Remember rectMode(CENTER) if you do.
 * 
 * @author Dezzmeister
 *
 */
class CollisionTest {
	Entity rect;
	Entity circ;
	Line line;
	
	Entity rect2;
	Entity circ2;
	Entity line2;
	
	@BeforeEach
	void setUp() throws Exception {
		rect = new Obstacle(250,250,50,200);
		circ = new Obstacle(200,200,75,75,Shape.CIRCLE);
		line = new Line(100,100,400,400);
		
		rect2 = new Obstacle(250,200,400,50);
		circ2 = new Obstacle(225,370,101,101);
		line2 = new Line(500,100,0,400);
	}

	@Test
	void testHasCollided() {
		
		assertTrue(Physics.collider.hasCollided(circ, rect));
		
		circ.placeAt(300, 370);		
		assertTrue(Physics.collider.hasCollided(rect, circ));
		//Circle and Rectangle has been tested before, and they work fine
		
		assertTrue(Physics.collider.hasCollided(rect, rect2));
		assertTrue(Physics.collider.hasCollided(circ2, circ2));
		
		assertTrue(Physics.collider.hasCollided(line, line2)); //TODO both lines should return true. Probably a fundamental error in Line
		//assertTrue(Physics.collider.hasCollided(circ, line)); //TODO line and circ should return true
	}

}
