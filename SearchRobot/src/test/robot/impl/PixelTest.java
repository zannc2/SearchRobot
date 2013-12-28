package test.robot.impl;

import static org.junit.Assert.assertEquals;
import helper.Position;
import helper.Size;

import org.junit.Test;

import robot.impl.Pixel;


public class PixelTest {

	private int x = 30;
	private int y = 40;
	private Position p = new Position(x, y);
	
	/* get Size */
	@Test
	public void testNewPixel() {
		Pixel pixel = new Pixel(p, null);
		
		// test if the constructor sets the right position & size
		Size s = new Size(10, 10);
		assertEquals(s, pixel.getSize());
		assertEquals(p, pixel.getPosition());
	}
}
