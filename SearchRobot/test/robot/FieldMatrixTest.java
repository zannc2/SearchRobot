package robot;

import frontend.classes.items.Line;
import frontend.classes.items.Robot;
import frontend.classes.view.Field;
import frontend.classes.view.ViewImpl;
import frontend.interfaces.Item;
import frontend.interfaces.View;
import helper.Position;
import helper.Size;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

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
		this.view = new ViewImpl(this.fieldSize, this.robotSize, this.itemColor);
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
