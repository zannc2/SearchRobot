package test.frontend.impl.items.selection;

import static org.junit.Assert.*;
import frontend.impl.items.selection.SelectionArea;
import helper.Position;
import helper.Size;
import helper.Vector;

import org.junit.Before;
import org.junit.Test;

public class SelectionAreaTest {

	private int x = 10;
	private int y = 10;
	private Position p = new Position(x, y);
	private SelectionArea sa;
	
	@Before
	public void setup () {	
		sa = new SelectionArea(p);
	}
	
	/**
	 * Test method for a new selection area
	 */
	@Test
	public void testNewSelectionArea() {
		Size s = new Size(0, 0);
		assertEquals(s, sa.getSize());
		assertEquals(p, sa.getPosition());
	}
	
	/* test set & get Size */
	@Test
	public void testGetSize() {
		Size s = new Size(10, 10);
		sa.setSize(s);
		assertEquals(s, sa.getSize());
	}
	
	/* test set & get Position */
	@Test
	public void testGetPosition() {
		Position p2 = new Position(50, 50);
		sa.setPosition(p2);
		assertEquals(p2, sa.getPosition());
	}

	/* test contains */
	@Test
	public void testContains() {
		Size s = new Size(20, 20);
		sa.setSize(s);
		assertTrue(sa.contains(p));
		Position p2 = new Position(20, 20);
		assertTrue(sa.contains(p2));
		
		Position p3 = new Position(50, 50);
		assertFalse(sa.contains(p3));
	}
	
	/* test selection from point 10/10 to top left */
	@Test
	public void testSelectionToTopLeft() {
		Size s = new Size(-10, -10);
		Position p2 = new Position(5, 5);
		sa.setSize(s);
		assertTrue(sa.contains(p2));
	}
	
	/* test selection from point 10/10 to top right*/
	@Test
	public void testSelectionToTopRight() {
		Size s = new Size(10, -10);
		Position p2 = new Position(15, 5);
		sa.setSize(s);
		assertTrue(sa.contains(p2));
	}
	
	/* test selection from point 10/10 to bottom left*/
	@Test
	public void testSelectionToBottomLeft() {
		Size s = new Size(-10, 10);
		Position p2 = new Position(5, 15);
		sa.setSize(s);
		assertTrue(sa.contains(p2));
	}
	
	/* test selection from point 10/10 to bottom right*/
	@Test
	public void testSelectionToBottomRight() {
		Size s = new Size(10, 10);
		Position p2 = new Position(15, 15);
		sa.setSize(s);
		assertTrue(sa.contains(p2));
	}
	
	/* test not implemented methods */
	@Test
	public void testMethodsNotImplemented() {
		// handler should be null
		assertNull(sa.getItemHandler());
		
		// move should change nothing
		sa.move(new Vector(new Position(50, 50)));
		assertEquals(p, sa.getPosition());
	}
}
