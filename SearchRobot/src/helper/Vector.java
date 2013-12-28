package helper;

import java.io.Serializable;

public class Vector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4167398541378044638L;
	private int xComponent;
	private int yComponent;
	
	public Vector(int deltaX, int deltaY) {
		this.xComponent = deltaX;
		this.yComponent = deltaY;
	}
	
	public Vector(Position p) {
		super();
		this.xComponent = p.getOriginX();
		this.yComponent = p.getOriginY();
	}
	
	public int getXComponent() {
		return this.xComponent;
	}
	
	public int getYComponent() {
		return this.yComponent;
	}

	public Vector getNormalVector() {
		return new Vector(-this.yComponent, this.xComponent);
	}

	public Vector plus(Vector b) {
		return new Vector(this.xComponent + b.xComponent, this.yComponent
				+ b.yComponent);
	}

	public Vector minus(Vector b) {
		return new Vector(this.xComponent - b.xComponent, this.yComponent
				- b.yComponent);
	}
	
	public double getDeterminantBy(Vector b) {
		return this.xComponent * b.yComponent - b.xComponent * this.yComponent;
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(this.xComponent, 2)
				+ Math.pow(this.yComponent, 2));
	}

}
