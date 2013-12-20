package test.frontend.impl.items;

import static org.junit.Assert.*;
import frontend.impl.items.Finish;
import frontend.impl.view.Field;
import frontend.impl.view.ItemChangedEvent;
import frontend.interfaces.ItemChangedListener;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Test;

public class FinishTest {
	
	private int x = 10;
	private int y = 11;
	private Position p = new Position(x, y);
	private Finish f = new Finish(p, new Field(new Size(200, 200), null));
	
	private boolean test;
	
	public class MyListener implements ItemChangedListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4701496235786388421L;

		@Override
		public void itemChanged(ItemChangedEvent e) {
			test = true;
		}

	}

	@Test
	public void testSize() {
		Size s = new Size(20,20);
		assertEquals(f.getSize(), s);
		s = new Size(20, 20);
		f.setSize(s);
		assertEquals(f.getSize(),s);
	}
	
	@Test
	public void testContains() {
		Position p2 = new Position(12, 13);
		assertTrue(f.contains(p2));
		Position p3 = new Position(40, 40);
		assertFalse(f.contains(p3));
	}

	@Test
	public void testMove() {
		MyListener listener = new MyListener();
		f.addItemChangedListener(listener);
		Vector v = new Vector(10, 25);
		Position newP = new Position(p.getOriginX() + 10, p.getOriginY() + 25);
		Finish newFinish = new Finish(newP, null);
		f.move(v);
		assertTrue(test);
		assertEquals(f.getPosition(), newFinish.getPosition());
	}
	
	@Test
	public void testSetPositionOutside() {
		assertEquals(p, f.getPosition());
		Position pos = new Position(300, 300);
		f.setPosition(pos);
		
		// still on the old position, cause 300/300 is outside of the field
		assertEquals(p, f.getPosition());
		
		Position pos2 = new Position(-50, -50);
		f.setPosition(pos2);
		
		// still on the old position, cause -50/-50 is outside of the field
		assertEquals(p, f.getPosition());
	}
	
	@Test
	public void testGetItemHandler() {
		assertTrue(f.getItemHandler() == null);
	}
}
