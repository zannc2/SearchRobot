package helper;

public enum Direction {
	NORTHWEST(Math.toRadians(225)),
	NORTHEAST(Math.toRadians(315)),
	SOUTHWEST(Math.toRadians(135)),
	SOUTHEAST(Math.toRadians(45)),
	NORTH(Math.toRadians(270)),
	EAST(Math.toRadians(0)),
	SOUTH(Math.toRadians(90)),
	WEST(Math.toRadians(180));
	
	private double direction;
	
	private Direction(double d) {
		this.direction = d;
	}

	public double getDirection() {
		return direction;
	}
}
