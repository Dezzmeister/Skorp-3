package com.dezzy.skorp3.skorp3D.data;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.game3D.Updateable;
import com.dezzy.skorp3.math3D.datastructures.Collapsable;

public class VBOList implements Collapsable<VBO>, Updateable {
	private List<VBO> list = new ArrayList<VBO>();
	private boolean updated = true;
	
	@Override
	public void update() {
		updated = true;
	}

	@Override
	public boolean hasUpdated() {
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}

	@Override
	public VBO collapse() {
		VBO vbo = new VBO();
		for (VBO v : list) {
			vbo.addAll(v);
		}
		return vbo;
	}
	
	public void add(VBO vbo) {
		update();
		list.add(vbo);
	}
	
	public VBO remove(int index) {
		update();
		return list.remove(index);
	}
	
	public void remove(VBO vbo) {
		update();
		list.remove(vbo);
	}
	
	public VBO set(int index, VBO vbo) {
		update();
		return list.set(index, vbo);
	}
	
	public int size() {
		return list.size();
	}
}
