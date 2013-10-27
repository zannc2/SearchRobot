package frontend.items;

import static org.junit.Assert.*;
import frontend.classes.items.Circle;
import frontend.classes.items.Finish;
import frontend.classes.view.ItemChangedEvent;
import frontend.interfaces.ItemChangedListener;
import frontend.items.CircleTest.MyListener;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Test;

public class FinishTest {
	
	private int x = 10;
	private int y = 11;
	private Position p = new Position(x, y);
	private Finish f = new Finish(p);
	
	private boolean test;
	
	public class MyListener implements ItemChangedListener {

		@Override
		public void itemChanged(ItemChangedEvent e) {
			test = true;
		}
		
	}

	@Test
	public void testSize() {
		Size s = new Size(8,8);
		assertEquals(f.getSize(), s);
		s = new Size(20, 20);
		f.setSize(s);
		assertEquals(f.getSize(),s);
	}
	
	@Test
	public void testContains() {
		Position p2 = new Position(12, 13);
		assertTrue(f.contains(p2));
	}

	@Test
	public void testMove() {
		MyListener listener = new MyListener();
		f.addItemChangedListener(listener);
		Vector v = new Vector(10, 25);
		Position newP = new Position(p.getOriginX() + 10, p.getOriginY() + 25);
		Finish newFinish = new Finish(newP);
		f.move(v);
		assertTrue(test);
		assertEquals(f.getPosition(), newFinish.getPosition());
	}
}
