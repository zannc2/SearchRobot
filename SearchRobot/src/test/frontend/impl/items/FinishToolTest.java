package test.frontend.impl.items;

import static org.junit.Assert.*;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frontend.impl.items.FinishTool;
import frontend.impl.items.Line;
import frontend.impl.view.Field;

public class FinishToolTest {
	
	Field field;
	
	@Before
	public void setup () {	
		field = new Field(new Size(600, 300), new Size(10, 10));
	}

	@Test
	public void testFinishTool() {
		FinishTool ft = new FinishTool(field);
		assertEquals(field, ft.getField());
	}

	@Test
	public void testMouseDown() {
		FinishTool ft = new FinishTool(field);
		ft.mouseDown(new Position(10, 10));
		assertEquals(new Position(10, 10), ft.getField().getItems().get(0).getPosition());
		assertEquals(new Size(20, 20), ft.getField().getItems().get(0).getSize());

		// set positon at 25/25 -> finish tool rounds down to 20/20
		ft.mouseDown(new Position(25, 25));
		assertEquals(new Position(20, 20), ft.getField().getItems().get(0).getPosition());
		
		// set position from finish on a existing LineItem -> don't work
		field.addItem(new Line(new Position(40, 40), field));
		ft.mouseDown(new Position(40, 40));
		assertEquals(new Position(20, 20), ft.getField().getItems().get(0).getPosition());
		
	}
}
