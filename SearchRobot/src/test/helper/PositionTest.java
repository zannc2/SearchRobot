package test.helper;

import static org.junit.Assert.*;
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
		
		// equals with two equal positions
		assertTrue(posA.equals(posB));
		
		// equals with null
		assertFalse(posA.equals(null));
		
		// equals with wrong class
		assertFalse(posA.equals(new Integer(1)));
		
		// equals with different origin y or x
		posB = new Position(x, 10);
		assertFalse(posA.equals(posB));
		posB = new Position(10, y);
		assertFalse(posA.equals(posB));
		
	}
}
