package frontend.impl.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import frontend.impl.items.Finish;
import frontend.impl.items.Line;
import frontend.impl.items.LineTool;
import frontend.impl.items.Robot;
import frontend.interfaces.Item;
import frontend.interfaces.StateFactory;
import frontend.interfaces.Tool;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ViewImplTest {

	Size robotSize, fieldSize; 
	ViewImpl vi;
	
	@Before
	public void setup () {	
		robotSize = new Size(10, 10);
		fieldSize = new Size(500, 300);
		vi =  new ViewImpl(fieldSize, robotSize, Color.BLACK);
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
		Item line1 = new Line(new Position(10, 10), new Field(null, null));
		Item line2 = new Line(new Position(10, 10), new Field(null, null));
		vi.addToSelection(line1);
		vi.addToSelection(line2);
		
		assertEquals(line1, vi.getSelection().get(0));
		assertEquals(line2, vi.getSelection().get(1));
		assertTrue(vi.getSelection().size() == 2);
		
		vi.removeFromSelection(line2);
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
		fail("Not yet implemented");
	}

	@Test
	public void testSetField() {
		Item line1 = new Line(new Position(10, 10), new Field(null, null));
		Item line2 = new Line(new Position(20, 20), new Field(null, null));
		List<Item> itemList = new LinkedList<>();
		itemList.add(line1);
		itemList.add(line2);
		
		Size newFieldSize = new Size(300, 300);
		vi.setField(itemList, newFieldSize);
		assertEquals(itemList.get(0), vi.getField().getItems().get(0));
		assertEquals(newFieldSize, vi.getField().getFieldSize());
		
	}

	@Test
	public void testSetFieldSize() {
		assertEquals(fieldSize, vi.getSize());
		
		Size newSize = new Size(400, 400);
		vi.setFieldSize(newSize);
		assertEquals(newSize, vi.getSize());
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
