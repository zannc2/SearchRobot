package test.helper;

import static org.junit.Assert.assertTrue;
import helper.Direction;

import org.junit.Test;

public class DirectionTest {

	private double north = Math.toRadians(270);
	private double south = Math.toRadians(90);
	private double west = Math.toRadians(180);
	private double east = Math.toRadians(0);
	
	@Test
	public void testDirections() {
		assertTrue(north == Direction.NORTH.getDirection());
		assertTrue(south == Direction.SOUTH.getDirection());
		assertTrue(west == Direction.WEST.getDirection());
		assertTrue(east == Direction.EAST.getDirection());
	}
}
