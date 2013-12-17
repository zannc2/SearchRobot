package frontend.impl.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import frontend.impl.view.ItemChangedEvent;
import frontend.interfaces.ItemChangedListener;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Test;

public class CircleTest {
	
	private int x = 10;
	private int y = 11;
	private Position p = new Position(x, y);
	private Circle c = new Circle(p, null);
	
	private boolean test;
	
	public class MyListener implements ItemChangedListener {

		@Override
		public void itemChanged(ItemChangedEvent e) {
			test = true;
		}
		
	}

	/**
	 * Test set and get Size
	 */
	@Test
	public void testGet() {
		Size s = new Size(1, 1);
		assertEquals(c.getSize(), s);
		s = new Size(10, 20);
		c.setSize(s);
		assertEquals(c.getSize(), s);
		s = new Size(20, 10);
		c.setSize(s);
		assertEquals(c.getSize(), s);
		s = new Size(11, 15);
		c.setSize(s);
		assertEquals(c.getSize(), s);
		s = new Size(11, 14);
		c.setSize(s);
		assertEquals(c.getSize(), s);
	}
	
	@Test
	public void testContains() {
		Size s = new Size(5, 5);
		c.setSize(s);
		Position p2 = new Position(12, 13);
		assertTrue(c.contains(p2));
	}
	
	@Test
	public void testMove() {
		MyListener listener = new MyListener();
		c.addItemChangedListener(listener);
		Vector v = new Vector(10, 25);
		Position newP = new Position(p.getOriginX() + 10, p.getOriginY() + 25);
		Circle newCircle = new Circle(newP, null);
		c.move(v);
		assertTrue(test);
		assertEquals(c.getPosition(), newCircle.getPosition());
	}

}
