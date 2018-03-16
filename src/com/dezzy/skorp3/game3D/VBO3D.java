package com.dezzy.skorp3.game3D;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game.Physics;

public class VBO3D {
	private List<Entity3D> objects;
	private String name;
	
	public VBO3D(String _name) {
		name = _name;
		objects = new ArrayList<Entity3D>();
	}
	
	public void add(Entity3D entity) {
		objects.add(entity);
	}
	
	public List<Entity3D> get() {
		return objects;
	}
	
	public String name() {
		return name;
	}
	
	public void render(int i, Graphics graphics) {
		Entity3D entity = objects.get(i);
		Physics.render(entity, graphics);
	}
	
	public void renderAll(Graphics graphics) {
		objects.forEach((entity) -> Physics.render(entity, graphics));
	}
}
