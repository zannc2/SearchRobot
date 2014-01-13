package helper;

/**
 * Class which defines the directions of the robot
 * 
 * @author zannc2 & gfels4
 *
 */
public enum Direction {
	NORTH(Math.toRadians(270)),
	EAST(Math.toRadians(0)),
	SOUTH(Math.toRadians(90)),
	WEST(Math.toRadians(180));
	
	private double direction;
	
	private Direction(double d) {
		this.direction = d;
	}

	/**
	 * Getter for the actual direction
	 * @return the direction
	 */
	public double getDirection() {
		return direction;
	}
}
