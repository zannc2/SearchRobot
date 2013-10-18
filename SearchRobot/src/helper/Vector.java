package helper;

import jdt.framework.Vector;

public class Vector {
	private int xComponent;
	private int yComponent;
	
	public Vector(int deltaX, int deltaY) {
		this.xComponent = deltaX;
		this.yComponent = deltaY;
	}
	
	public int getXComponent() {
		return this.xComponent;
	}
	
	public int getYComponent() {
		return this.yComponent;
	}

}
