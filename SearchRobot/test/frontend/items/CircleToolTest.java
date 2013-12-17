package frontend.items;

import static org.junit.Assert.assertEquals;
import frontend.impl.items.CircleTool;
import frontend.impl.view.Field;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests methods of class CircleTool 
 */
public class CircleToolTest 
{

	Field field;

	@Before
	public void setup () {	
		field = new Field(new Size(600, 300), new Size(10, 10));
	}

	/**
	 * Test method for a new circle tool
	 */
	@Test
	public void testNewTool() {
		CircleTool ct = new CircleTool(field);
		assertEquals(field, ct.getField());
	}

	/**
	 * Test mousdrag event
	 */
	@Test
	public void testMouseDrag() {
		CircleTool ct = new CircleTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());

		// drag down right
		ct.mouseDrag(new Position(15, 15));
		assertEquals(new Size(5, 5), ct.getField().getItems().get(0).getSize());

		// drag up left (size is minus)
		ct.mouseDrag(new Position(5, 5));
		assertEquals(new Size(-5, -5), ct.getField().getItems().get(0).getSize());
	}

	/**
	 * Test method forCreating a Circle from top left to bottom right
	 */
	@Test
	public void testNewCircleToBottomRight() {
		CircleTool ct = new CircleTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());

		// circle from top left to bottom right
		ct.mouseUp(new Position(20,20));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(10, 10), ct.getField().getItems().get(0).getSize());
	}

	/**
	 * Test method forCreating a Circle from bottom right to top left
	 */
	@Test
	public void testNewCircleToTopLeft() {
		CircleTool ct = new CircleTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());

		// circle from bottom right to top left
		ct.mouseUp(new Position(0,0));
		assertEquals(new Position(0, 0), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(10, 10), ct.getField().getItems().get(0).getSize());
	}

	/**
	 * Test method forCreating a circle from bottom left to top right
	 */
	@Test
	public void testNewCircleToTopRight() {
		CircleTool ct = new CircleTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());

		// circle from bottom left to top right
		ct.mouseUp(new Position(20,0));
		assertEquals(new Position(10, 0), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(10, 10), ct.getField().getItems().get(0).getSize());
	}

	/**
	 * Test method forCreating a Circle from top right to bottom left
	 */
	@Test
	public void testNewCircleToBottomLeft() {
		CircleTool ct = new CircleTool(field);
		ct.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(1, 1), ct.getField().getItems().get(0).getSize());

		// circle from top right to bottom left
		ct.mouseUp(new Position(0,20));
		assertEquals(new Position(0, 10), ct.getField().getItems().get(0).getPosition());
		assertEquals(new Size(10, 10), ct.getField().getItems().get(0).getSize());
	}
}