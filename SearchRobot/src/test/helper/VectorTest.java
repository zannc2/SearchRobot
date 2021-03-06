package test.helper;

import static org.junit.Assert.*;
import helper.Vector;

import org.junit.Test;

public class VectorTest {
	
	int xComponent = 7;
	int yComponent = 9;
	
	Vector v = new Vector(xComponent, yComponent);

	@Test
	public void testXComponent() {
		assertEquals(xComponent, v.getXComponent());
	}
	
	@Test
	public void testYComponent() {
		assertEquals(yComponent, v.getYComponent());
	}
	
	@Test
	public void testPlus() {
		assertEquals(yComponent, v.getYComponent());
	}
	
	@Test
	public void testMinus() {
		assertEquals(yComponent, v.getYComponent());
	}
}
