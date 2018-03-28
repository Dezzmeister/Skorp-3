package com.dezzy.skorp3.game3D;

import java.util.ArrayList;

import com.dezzy.skorp3.math3D.datastructures.Collapsable;

public class VBO3DList extends ArrayList<VBO3D> implements Collapsable<VBO3D> {

	private static final long serialVersionUID = -6497313330789469433L;

	@Override
	public VBO3D collapse() {
		VBO3D vbo = new VBO3D("vbo");
		for (VBO3D v : this) {
			vbo.addAll(v);
		}
		return vbo;
	}

}
