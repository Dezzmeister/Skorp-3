package com.dezzy.skorp3.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.field.Entity;

public class VBO {
	private List<Entity> objects;
	private String name;
	
	public VBO(String _name) {
		name = _name;
		objects = new ArrayList<Entity>();
	}
	
	public void add(Entity entity) {
		objects.add(entity);
	}
	
	public List<Entity> get() {
		return objects;
	}
	
	public String name() {
		return name;
	}
	
	public void render(int i, Graphics graphics) {
		Entity entity = objects.get(i);
		Physics.render2D(entity, graphics);
	}
	
	public void renderAll(Graphics graphics) {
		objects.forEach((entity) -> Physics.render2D(entity, graphics));
	}
}
