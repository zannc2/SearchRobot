package test.frontend.impl.items;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.awt.Cursor;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Line;
import frontend.impl.items.RemoveTool;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;


public class RemoveToolTest {

	Field field;
	View view;

	@Before
	public void setup () {	
		view = new View(new Size(600, 300), new Size(10, 10), Color.RED);
		field = view.getField();
	}

	/**
	 * Test method for a new circle tool
	 */
	@Test
	public void testNewTool() {
		RemoveTool ct = new RemoveTool(field);
		assertEquals(field, ct.getField());
	}

	/**
	 * Test mousdown event
	 */
	@Test
	public void testMouseDown() {
		Item line = new Line(new Position(10, 10), field);
		line.setSize(new Size(20, 0));
		field.addItem(line);
		assertTrue(field.getItems().size() == 1);
		
		RemoveTool rt = new RemoveTool(field);
		
		// test mouse down event at a position where is not an item
		rt.mouseDown(new Position(30,50));
		assertTrue(field.getItems().size() == 1);
		
		// test mouse down event at a position where is an item
		rt.mouseDown(new Position(20, 10));
		assertTrue(field.getItems().size() == 0);
	}

	/**
	 * Test mousover event
	 */
	@Test
	public void testMouseOver() {
		Item line = new Line(new Position(10, 10), field);
		line.setSize(new Size(20, 0));
		field.addItem(line);
		
		RemoveTool rt = new RemoveTool(field);
		rt.mouseOver(new Position(10, 10));
		assertEquals(Cursor.HAND_CURSOR, rt.getField().getView().getCursor().getType());
		
		rt.mouseOver(new Position(30, 30));
		assertEquals(Cursor.DEFAULT_CURSOR, rt.getField().getView().getCursor().getType());
	}
}
