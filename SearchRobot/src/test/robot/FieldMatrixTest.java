package test.robot;

import static org.junit.Assert.fail;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Line;
import frontend.impl.items.Robot;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;

public class FieldMatrixTest {
	
	private View view;
	private Field field;
	private Item itemA;
	private Item itemB;
	private Item robot;
	private Position robotPosition;
	private List<Item> items = new ArrayList<Item>();
	private Size robotSize = new Size(10, 10);
	private Size fieldSize = new Size(600, 800);
	private Color itemColor = Color.black;
	
	@Before
    public void setUp() {
		this.view = new View(this.fieldSize, this.robotSize, this.itemColor);
		this.field = new Field(this.view, this.fieldSize, this.robotSize);
		
		this.itemA = new Line(new Position(10, 10), null);
		this.itemA.setSize(new Size(50, 10));
		this.items.add(itemA);
		this.field.addItem(itemA);
		
		this.itemB = new Line(new Position(50,70), null);
		this.itemB.setSize(new Size(10,50));
		this.items.add(itemB);
		this.field.addItem(itemB);
		
		this.robotPosition = new Position(100, 100);
		this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
		this.items.add(robot);
		this.field.addItem(robot);
    }

	/**
	 * Test if 
	 */
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
