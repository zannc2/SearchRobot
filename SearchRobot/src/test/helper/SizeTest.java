package test.helper;

import static org.junit.Assert.*;
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
		assertTrue(s.equals(s2));
	}

}
