package com.dezzy.skorp3.game3D;

public class Data3D {
	private int zBufferLimit = 0;
	public boolean perspectiveMode = false;
	public int fovAngle = 90;
	
	public Data3D(boolean _perspectiveMode, int _fovAngle) {
		perspectiveMode = _perspectiveMode;
		fovAngle = _fovAngle;
	}
	
	public void zBufferLimit(int limit) {
		zBufferLimit = limit;
	}
	
	public int zBufferLimit() {
		return zBufferLimit;
	}
}
