package com.dezzy.skorp3.skorp3D.data;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.game3D.Updateable;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.math3D.datastructures.Collapsable;

public class VBOList implements Collapsable<VBO3D>, Updateable {
	private List<VBO3D> list = new ArrayList<VBO3D>();
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
	public VBO3D collapse() {
		VBO3D vbo = new VBO3D("vbo");
		for (VBO3D v : list) {
			vbo.addAll(v);
		}
		return vbo;
	}
	
	public void add(VBO3D vbo) {
		update();
		list.add(vbo);
	}
	
	public VBO3D remove(int index) {
		update();
		return list.remove(index);
	}
	
	public void remove(VBO3D vbo) {
		update();
		list.remove(vbo);
	}
	
	public VBO3D set(int index, VBO3D vbo) {
		update();
		return list.set(index, vbo);
	}
	
	public int size() {
		return list.size();
	}
}
