package frontend.impl.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import frontend.impl.view.ItemChangedEvent;
import frontend.interfaces.ItemChangedListener;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Test;


public class LineTest {
	
	private int x = 10;
	private int y = 11;
	private int width = 5;
	private int height = 7;
	private Size s = new Size(width, height);
	private Position p = new Position(x, y);
	private Line l = new Line(p, null);

	private boolean test;
	
	public class MyListener implements ItemChangedListener {

		@Override
		public void itemChanged(ItemChangedEvent e) {
			test = true;
		}
		
	}
	
	/* test set & get Size */
	@Test
	public void testGet() {
		Size s2 = new Size(1, 1);
		assertEquals(l.getSize(), s2);
		l.setSize(s);
		assertEquals(l.getSize(), s);
	}

	/* test contains */
	@Test
	public void testContains() {
		l.setSize(s);
		assertTrue(l.contains(p));
	}
	
	/* test move */
	@Test
	public void testMove() {
		MyListener listener = new MyListener();
		l.addItemChangedListener(listener);
		Vector v = new Vector(10, 25);
		Position newP = new Position(p.getOriginX() + 10, p.getOriginY() + 25);
		Line newLine = new Line(newP, null);
		l.move(v);
		assertTrue(test);
		assertEquals(l.getPosition(), newLine.getPosition());
	}
	
	

}
