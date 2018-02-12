package com.dezzy.skorp3.game;

/**
 * Physics governs game physics (or what little there is so far).
 * 
 * Physics is meant to follow the OOP delegation model and provide public access to package-private
 * physics modules, such as CollisionHandler. If you wish to add functionality, add to a specific module if you can
 * before you create a new one. For example, do not create a physics module to test for collisions between lines and circles;
 * it would be better implemented in CollisionHandler.
 * 
 * @author Dezzmeister
 *
 */
public final class Physics {
	public static CollisionHandler collider = new CollisionHandler();
}
