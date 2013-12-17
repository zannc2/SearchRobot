package frontend.items;

import static org.junit.Assert.assertEquals;
import frontend.impl.items.LineTool;
import frontend.impl.view.Field;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.Test;

public class LineToolTest {

	Field field;

	@Before
	public void setup () {	
		field = new Field(new Size(600, 300), new Size(10, 10));
	}

	/**
	 * Test method for a new linetool
	 */
	@Test
	public void testNewTool() {
		LineTool lt = new LineTool(field);
		assertEquals(field, lt.getField());
	}

	/**
	 * Test mousdrag event
	 */
	@Test
	public void testMouseDrag() {
		LineTool lt = new LineTool(field);
		lt.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), lt.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), lt.getField().getItems().get(0).getSize());

		// drag down right
		lt.mouseDrag(new Position(15, 15));
		assertEquals(new Size(5, 5), lt.getField().getItems().get(0).getSize());

		// drag up left (size is minus)
		lt.mouseDrag(new Position(5, 5));
		assertEquals(new Size(-5, -5), lt.getField().getItems().get(0).getSize());
	}


	/**
	 * Test method forCreating a new Line with positive size
	 */
	@Test
	public void testNewLinePositiveSize() {
		LineTool lt = new LineTool(field);
		lt.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), lt.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), lt.getField().getItems().get(0).getSize());
		
		lt.mouseDrag(new Position(10, 11));
		lt.mouseDrag(new Position(10, 12));
		lt.mouseDrag(new Position(11, 12));
		lt.mouseDrag(new Position(12, 12));
		lt.mouseDrag(new Position(13, 12));
		lt.mouseDrag(new Position(14, 12));
		lt.mouseDrag(new Position(14, 13));
		lt.mouseDrag(new Position(15, 13));
		lt.mouseDrag(new Position(15, 14));

		// circle from top left to bottom right
		lt.mouseUp(new Position(15,15));
		assertEquals(new Position(10, 10), lt.getField().getItems().get(0).getPosition());
		assertEquals(new Size(5,5), lt.getField().getItems().get(0).getSize());
	}
	
	/**
	 * Test method forCreating a new Line with negative size
	 */
	@Test
	public void testNewLineNegativeSize() {
		LineTool ct = new LineTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());
		
		ct.mouseDrag(new Position(9, 10));
		ct.mouseDrag(new Position(8, 10));
		ct.mouseDrag(new Position(7, 10));
		ct.mouseDrag(new Position(7, 9));
		ct.mouseDrag(new Position(7, 8));
		ct.mouseDrag(new Position(7, 7));
		ct.mouseDrag(new Position(6, 7));
		ct.mouseDrag(new Position(6, 6));
		ct.mouseDrag(new Position(5, 6));

		ct.mouseUp(new Position(5,5));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(-5,-5), ct.getField().getItems().get(0).getSize());
	}
}
