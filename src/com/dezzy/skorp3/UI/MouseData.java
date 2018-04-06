package com.dezzy.skorp3.UI;

import com.dezzy.skorp3.game3D.Updateable;

public class MouseData implements Updateable {
	private int x;
	private int y;
	private boolean updated = true;
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public void x(int _x) {
		if (x != -x) update();
		x = _x;
	}
	
	public void y(int _y) {
		if (y != _y) update();
		y = _y;
	}

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
}
