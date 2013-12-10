package frontend.impl.view;

import frontend.impl.items.Circle;
import frontend.impl.items.Finish;
import frontend.impl.items.Line;
import frontend.impl.items.Robot;
import frontend.interfaces.FieldChangedListener;
import frontend.interfaces.Item;
import frontend.interfaces.View;
import helper.Direction;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FieldTest {
	
	private View view;
	private Field field;
	private Item itemA;
	private Item itemB;
	private Item robot;
	private Position robotPosition;
	private Direction robotDirection = Direction.WEST;
	private List<Item> items = new ArrayList<>();
	private Size robotSize = new Size(10, 10);
	private Size fieldSize = new Size(600, 800);
	private Color itemColor = Color.black;
	
	private class MockSheetChangedListener implements FieldChangedListener {
		public int countCalled = 0;

		@Override
		public void fieldChanged(FieldChangedEvent e) {
			this.countCalled++;
		}
	}
	
	private MockSheetChangedListener listener;
	
	@Before
    public void setUp() {
		this.view = new ViewImpl(this.fieldSize, this.robotSize, this.itemColor);
		this.field = new Field(this.view, this.fieldSize, this.robotSize);
		
		this.itemA = new Line(new Position(10, 10), null);
		this.itemA.setSize(new Size(50, 25));
		this.items.add(itemA);
		this.field.addItem(itemA);
		
		this.itemB = new Circle(new Position(50,70), null);
		this.itemB.setSize(new Size(20,20));
		this.items.add(itemB);
		this.field.addItem(itemB);
		
		this.robotPosition = new Position(100, 100);
		this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
		this.items.add(robot);
		this.field.addItem(robot);
		
		this.listener = new MockSheetChangedListener();
    }

	/**
	 * Test Getters and Setters
	 */
	@Test 
	public void testGetter() {
		// test getView
		assertEquals(this.field.getView(), this.view);
		
		//test getFieldSize & setFieldSize
		assertEquals(this.field.getFieldSize(), this.fieldSize);
		Size newFieldSize = new Size(100, 100);
		this.field.setFieldSize(newFieldSize);
		assertEquals(this.field.getFieldSize(), newFieldSize);
		this.field.setFieldSize(this.fieldSize);
		assertEquals(this.field.getFieldSize(), this.fieldSize);
	}
	
	/**
	 * Test Item Functions
	 */
	@Test 
	public void testItemFunctions() {
		//test getItems
		assertEquals(this.field.getItems(), this.items);
		
		//test removeItem
		this.field.removeItem(itemB);
		this.items.remove(itemB);
		assertEquals(this.field.getItems(), this.items);
		
		//test addITem
		this.field.addItem(itemB);
		this.items.add(itemB);
		this.items.remove(robot);
		this.items.add(robot);
		assertEquals(this.field.getItems(), this.items);
	}
	
	/**
	 * Test Robot Functions
	 */
	@Test 
	public void testRobotFunctions() {	
		//test getRobotSize
		assertEquals(this.field.getRobotSize(), this.robotSize);
		
		//test getRobotPosition
		assertEquals(this.field.getRobotPosition(), this.robotPosition);
		
		//test setRobotPosition
		Position newPosition = new Position(50, 50);
		this.field.setRobotPosition(newPosition);
		assertEquals(this.field.getRobotPosition(), newPosition);
		this.field.setRobotPosition(this.robotPosition);
		
		//test getRobotDirection
		assertEquals(this.field.getRobotDirection(), this.robotDirection);
		
		//test setRobotDirection
		this.robotDirection = Direction.SOUTH;
		this.field.setRobotDirection(this.robotDirection);
		assertEquals(this.field.getRobotDirection(), this.robotDirection);	
	}
	
	/**
	 * Test Listener Functions
	 */
	@Test 
	public void testListenerFunctions() {
		//no Listener added -> after notify listener.countCalled = 0
		this.field.removeItem(itemA);
		assertEquals(this.listener.countCalled, 0);
		
		//add listener -> after notify listener.countCalled = 1
		this.field.addListener(this.listener);
		this.field.addItem(itemA);
		assertEquals(this.listener.countCalled, 1);
		
		//remove listener -> after notify listener.countCalled still 1
		this.field.removeListener(listener);
		this.field.removeItem(itemA);
		assertEquals(this.listener.countCalled, 1);
		
		//reset field items
		this.field.addItem(itemA);
	}
	
	/**
	 * test Contains Function
	 */
	@Test 
	public void testContains() {
		assertTrue(this.field.contains(this.robotPosition, 10));
		assertFalse(this.field.contains(new Position(5, 5), 10));
	}
	
	/**
	 * test check Function
	 * chekIfPositionFree and checkMoveItem
	 */
	@Test 
	public void testCkecksFunction() {
		//chekIfPositionFree
		Item finish = new Finish(this.robotPosition, this.field);
		assertFalse(this.field.checkIfPositionFree(finish));
		
		finish.setPosition(new Position(50, 70));
		assertFalse(this.field.checkIfPositionFree(finish));
		
		finish.setPosition(new Position(200, 200));
		assertTrue(this.field.checkIfPositionFree(finish));	
	}
}
