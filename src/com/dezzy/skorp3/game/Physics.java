package com.dezzy.skorp3.game;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game3D.CollisionHandler3D;
import com.dezzy.skorp3.game3D.Renderer3D;
import com.dezzy.skorp3.game3D.VBO3D;

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
	private static CollisionHandler collider = new CollisionHandler();
	private static CollisionHandler3D collider3D = new CollisionHandler3D();
	private static Renderer renderer = new Renderer();
	private static Renderer3D renderer3D = new Renderer3D();
	
	public static boolean hasCollided(Entity entity1, Entity entity2) {
		return collider.hasCollided(entity1, entity2);
	}
	
	public static boolean hasCollided(Entity3D entity1, Entity3D entity2) {
		return collider3D.hasCollided(entity1,entity2);
	}
	
	public static void render(Entity entity, Graphics graphics) {
		renderer.render(entity, graphics);
	}
	
	public static void barycentricRaster(VBO3D vbo, Graphics graphics, JPanel panel) {
		renderer3D.barycentricRaster(vbo, graphics, panel);
	}
	
	private Physics() {
		
	}
}
