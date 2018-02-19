package com.dezzy.skorp3.game;

import com.dezzy.skorp3.field.Entity;

/**
 * Physics governs game physics (or what little there is so far).
 * 
 * Physics is meant to follow the OOP delegation model and provide public access to package-private
 * physics modules, such as CollisionHandler. If you wish to add functionality, add to a specific module if you can
 * before you create a new one. For example, do not create a physics module to test for collisions between lines and circles;
 * it would be better implemented in CollisionHandler.
 * 
 * Under normal circumstances, Physics cannot be subclassed or instantiated.
 * 
 * @author Dezzmeister
 *
 */
public final class Physics {
	public static CollisionHandler collider = new CollisionHandler();
	
	public static boolean hasCollided(Entity entity1, Entity entity2) {
		return collider.hasCollided(entity1, entity2);
	}
	
	private Physics() {
		
	}
}
