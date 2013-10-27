package helper;

import java.awt.Point;


public class Position {
	private int originX;

	private int originY;
	
	public Position(int originX, int originY) {
		super();
		this.originX = originX;
		this.originY = originY;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}
	
	public Point getAWTPoint() {
		return new Point(this.originX, this.originY);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Position other = (Position) obj;
		if (originX != other.originX)
			return false;
		if (originY != other.originY)
			return false;
		return true;
	}

	public String toString() {
		return "Coord[" + this.originX + "," + this.originY + "]";
	}
}
