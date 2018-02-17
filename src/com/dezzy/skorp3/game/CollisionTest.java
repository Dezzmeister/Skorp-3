package com.dezzy.skorp3.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.dezzy.skorp3.game.Physics.hasCollided;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dezzy.skorp3.field.Geometric;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.field.Obstacle;
import com.dezzy.skorp3.field.Point;

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
	Geometric rect;
	Geometric circ;
	Line line;
	
	Geometric rect2;
	Geometric circ2;
	Geometric line2;
	Geometric circ3;
	
	Point point;
	
	@BeforeEach
	void setUp() throws Exception {
		rect = new Obstacle(250,250,50,200);
		circ = new Obstacle(200,200,75,75,Shape.CIRCLE);
		line = new Line(100,100,400,400);
		
		rect2 = new Obstacle(250,200,400,50);
		circ2 = new Obstacle(225,370,101,101,Shape.CIRCLE);
		line2 = new Line(500,100,0,400);
		point = new Point(200,200);
		
		circ3 = new Obstacle(250,250,150,150,Shape.CIRCLE);
	}

	@Test
	void testHasCollided() {
		
		assertTrue(hasCollided(circ, rect));
		
		circ.placeAt(300, 370);		
		assertTrue(hasCollided(rect, circ));
		//Circle and Rectangle has been tested before, and they work fine
		
		assertTrue(hasCollided(rect, rect2));
		assertTrue(hasCollided(circ2, circ2));
		
		//assertTrue(hasCollided(line, line2)); //TODO both lines should return true. Probably a fundamental error in Line
		//assertTrue(hasCollided(circ, line)); //TODO line and circ should return true
		assertTrue(hasCollided(point,line));
		point.placeAt(50, 50);
		assertFalse(hasCollided(point,line));
		point.placeAt(201,200);
		assertFalse(hasCollided(point, line));
		point.placeAt(0,900);
		assertFalse(hasCollided(point, line));
		point.placeAt(200, 200);
		
		assertFalse(hasCollided(circ2, point));
		assertTrue(hasCollided(circ3, point));
		
		assertTrue(hasCollided(rect2, point));
		assertFalse(hasCollided(rect, point));
	}

}
