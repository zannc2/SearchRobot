package frontend.impl.items;

import static org.junit.Assert.*;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frontend.impl.view.Field;

public class RobotToolTest {
	
	Field field;
	Size robotSize = new Size(10, 10);
	@Before
	public void setup () {	
		field = new Field(new Size(600, 300), new Size(10, 10));
	}

	@Test
	public void testRobotTool() {
		RobotTool rt = new RobotTool(field, new Size(10, 10));
		assertEquals(field, rt.getField());
	}

	@Test
	public void testMouseDown() {
		RobotTool rt = new RobotTool(field, new Size(10, 10));
		rt.mouseDown(new Position(10, 10));
		Robot r = (Robot) field.getItems().get(0);
		assertEquals(new Position(10, 10), r.getPosition());
		assertEquals(robotSize, r.getSize());

		// set positon at 20/20
		Position p = new Position(20, 20);
		rt.mouseDown(p);
		assertEquals(p, r.getPosition());
		
		// set position from robot on a existing LineItem -> don't work, its still on the old position
		field.addItem(new Line(new Position(40, 40), field));
		Position p2 = new Position(40, 40);
		rt.mouseDown(p2);
		
		assertEquals(p, r.getPosition());
		
	}

}
