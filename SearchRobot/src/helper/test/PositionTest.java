package helper.test;

import helper.Position;
import junit.framework.Assert;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testOrigin() {
		int x = 3;
		int y = 5;
		Position pos = new Position(x, y);
		
		Assert.assertEquals(x, pos.getOriginX());
		Assert.assertEquals(y, pos.getOriginY());
	}
	
	@Test
	public void testEquals() {
		int x = 3;
		int y = 5;
		Position posA = new Position(x, y);
		Position posB = new Position(x, y);
		Assert.assertTrue(posA.equals(posB));
	}

}
