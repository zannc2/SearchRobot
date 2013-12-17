package test.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helper.Position;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testOrigin() {
		int x = 3;
		int y = 5;
		Position pos = new Position(x, y);
		
		assertEquals(x, pos.getOriginX());
		assertEquals(y, pos.getOriginY());
	}
	
	@Test
	public void testEquals() {
		int x = 3;
		int y = 5;
		Position posA = new Position(x, y);
		Position posB = new Position(x, y);
		assertTrue(posA.equals(posB));
	}
}
