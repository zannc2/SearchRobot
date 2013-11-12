package helper;

public enum Direction {
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
