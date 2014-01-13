package test.helper;

import static org.junit.Assert.*;

import java.awt.Point;

import helper.Position;
import helper.Size;

import org.junit.Test;

public class SizeTest {
	
	int width = 6;
	int height = 7;
	
	Size s = new Size(width, height);

	@Test
	public void testHeight() {
		assertEquals(height, s.getHeight());
	}
	
	@Test
	public void testWidth() {
		assertEquals(width, s.getWidth());
	}
	
	@Test
	public void testEquals() {
		Size s2 = new Size(width, height);
		// equals with two equal sizes
		assertTrue(s.equals(s2));
		
		// equals with null
		assertFalse(s.equals(null));
		
		// equals with wrong class
		assertFalse(s.equals(new Integer(1)));
		
		// equals with different width/height
		s2 = new Size(width, 10);
		assertFalse(s.equals(s2));
		s2 = new Size(10, height);
		assertFalse(s.equals(s2));
	}
	
	@Test
	public void testGetAWTRectangle() {
		Position p = new Position(10,10);
		
		Size s = new Size(2, 1);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(10,10));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(1, 2);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(10,10));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(2, -1);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(10,8));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(1, -2);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(10,8));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		
		s = new Size(-2, 1);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(8,10));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(-1, 2);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(8,10));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(-2, -1);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(8,8));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
		
		s = new Size(-1, -2);
		assertEquals(Size.getAWTRectangle(s, p).getLocation(), new Point(8,8));
		assertTrue(Size.getAWTRectangle(s, p).height == 2);
		assertTrue(Size.getAWTRectangle(s, p).width == 2);
	}

}
