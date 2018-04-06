package com.dezzy.skorp3.game3D;

/**
 * Represents an object that closely monitors its state and allows
 * other objects to know about its state.
 * 
 * @author Dezzmeister
 *
 */
public interface Updateable {
	
	/**
	 * Updates the state of the object to indicate the need of a change or re-evaluation
	 * somewhere else. update() should be called in any method that will effect a change that must be
	 * dealt with immediately somewhere else. 
	 * <p>
	 * For instance, an <code>Entity</code> will call update() whenever a transformation
	 * is applied; VBO objects need to know about these transformations immediately. Instead
	 * of constantly asking the Entity to constantly send the latest triangles, the VBO only asks
	 * when it knows that the <code>Entity</code> has updated its triangles.
	 */
	public void update();
	
	/**
	 * Returns true if the object has updated. Should also revert the state
	 * of the object if it has updated.
	 * 
	 * @return true if the object has updated
	 */
	public boolean hasUpdated();
}
