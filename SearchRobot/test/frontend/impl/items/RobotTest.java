package frontend.impl.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import frontend.impl.view.Field;
import frontend.impl.view.ItemChangedEvent;
import frontend.interfaces.ItemChangedListener;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Test;

public class RobotTest {
	
	private int x = 10;
	private int y = 11;
	private Position p = new Position(x, y);
	private Robot r = new Robot(p, new Size(10,10), new Field(new Size(300, 300), null));
	
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
	public void testSize() {
		Size s = new Size(10,10);
		assertEquals(r.getSize(), s);
		s = new Size(20, 20);
		r.setSize(s);
		assertEquals(r.getSize(), s);
	}
	
	@Test
	public void testContains() {
		Position p2 = new Position(12, 13);
		assertTrue(r.contains(p2));
	}
	
	@Test
	public void testMove() {
		MyListener listener = new MyListener();
		r.addItemChangedListener(listener);
		Vector v = new Vector(10, 25);
		Position newP = new Position(p.getOriginX() + 10, p.getOriginY() + 25);
		Robot newRobot = new Robot(newP, new Size(10, 10), null);
		r.move(v);
		assertTrue(test);
		assertEquals(r.getPosition(), newRobot.getPosition());

	}

}
