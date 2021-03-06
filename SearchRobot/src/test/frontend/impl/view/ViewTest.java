package test.frontend.impl.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import frontend.impl.items.Circle;
import frontend.impl.items.Finish;
import frontend.impl.items.Line;
import frontend.impl.items.LineTool;
import frontend.impl.items.Robot;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.StateFactory;
import frontend.interfaces.Tool;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ViewTest {

	Size robotSize, fieldSize; 
	View vi;
	
	@Before
	public void setup () {	
		robotSize = new Size(10, 10);
		fieldSize = new Size(500, 300);
		vi =  new View(fieldSize, robotSize, Color.BLACK);
	}
	
	
	@Test
	public void testViewImpl() {
		assertEquals(fieldSize, vi.getField().getFieldSize());
		assertEquals(robotSize, vi.getField().getRobotSize());
		assertEquals(Color.BLACK, vi.getItemColor());
		
	}

	@Test
	public void testSetItemColor() {
		Color col = Color.RED;
		vi.setItemColor(col);
		assertEquals(col, vi.getItemColor());
	}

	@Test
	public void testPaintComponentGraphics() {
		// wie testen?
	}

	/**
	 * test set & get tool
	 */
	@Test
	public void testSetAndGetTool() {
		Field field = new Field(null, null);
		Tool t = new LineTool(field);
		vi.setTool(t);
		
		assertEquals(t, vi.getTool());
	}

	@Test
	public void testSelection() {
		Item line1 = new Line(new Position(10, 10), vi.getField());
		Item finish = new Finish(new Position(10, 10), vi.getField());
		vi.addToSelection(line1);
		vi.addToSelection(finish);
		
		assertEquals(line1, vi.getSelection().get(0));
		assertEquals(finish, vi.getSelection().get(1));
		assertTrue(vi.getSelection().size() == 2);
		
		// can not add an item twice
		vi.addToSelection(finish);
		assertTrue(vi.getSelection().size() == 2);
		
		vi.removeFromSelection(finish);
		assertTrue(vi.getSelection().size() == 1);
		
		vi.clearSelection();	
		assertTrue(vi.getSelection().size() == 0);
	}

	@Test
	public void testGetField() {
		assertTrue(vi.getField() instanceof Field);
	}

	@Test
	public void testGetStateFactory() {
		assertTrue(vi.getStateFactory() instanceof StateFactory);
	}
	
	@Test
	public void testGetSelectionHandles() {
		assertTrue(vi.getSelectionHandles().size() == 0);
		Item line1 = new Line(new Position(10, 10), vi.getField());
		vi.addToSelection(line1);
		assertFalse(vi.getSelectionHandles().size() == 0);
	}

	@Test
	public void testSetFieldCorrect() {
		// add a circle to the field
		Item circle = new Circle(new Position(10, 10), vi.getField());
		vi.getField().addItem(circle);
		assertTrue(vi.getField().getItems().size() == 1);
		
		// set field with a list of two lines, the existing circle should be removed
		Item line1 = new Line(new Position(10, 10), new Field(null, null));
		Item line2 = new Line(new Position(20, 20), new Field(null, null));
		List<Item> itemList = new LinkedList<>();
		itemList.add(line1);
		itemList.add(line2);
		Size newFieldSize = new Size(300, 300);
		vi.setField(itemList, newFieldSize);
		assertTrue(vi.getField().getItems().size() == 2);
		assertEquals(itemList.get(0), vi.getField().getItems().get(0));
		assertEquals(newFieldSize, vi.getField().getFieldSize());
	}
	
	@Test
	public void testSetFieldNull() {
		// add a circle to the field
		Item circle = new Circle(new Position(10, 10), vi.getField());
		vi.getField().addItem(circle);
		assertTrue(vi.getField().getItems().size() == 1);
		
		// set field with a list "null", the size of the itemlist must be 0 after the call of setField()
		List<Item> itemList = null;
		Size newFieldSize = new Size(300, 300);
		vi.setField(itemList, newFieldSize);
		assertTrue(vi.getField().getItems().size() == 0);
	}

	@Test
	public void testSetFieldSize() {
		assertEquals(new Dimension(fieldSize.getWidth(), fieldSize.getHeight()), vi.getPreferredSize());
		Size newSize = new Size(400, 400);
		vi.setFieldSize(newSize);
		assertEquals(new Dimension(newSize.getWidth(), newSize.getHeight()), vi.getPreferredSize());
	}

	@Test
	public void testHasRobotAndFinish() {
		assertFalse(vi.hasRobotAndFinish());
		
		vi.getField().addItem(new Robot(null, null, null));
		assertFalse(vi.hasRobotAndFinish());
		
		vi.getField().addItem(new Finish(null, null));
		assertTrue(vi.hasRobotAndFinish());
	}

}
