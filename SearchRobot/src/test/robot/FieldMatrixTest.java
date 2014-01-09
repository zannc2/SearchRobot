package test.robot;

import static org.junit.Assert.assertEquals;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import robot.impl.FieldMatrix;
import frontend.impl.items.Finish;
import frontend.impl.items.Line;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;

public class FieldMatrixTest {
	
	private View view;
	private Field field;
	private Item itemA;
	private Item itemB;
	private Item finish;
	private Position finishPosition;
	private List<Item> items = new ArrayList<Item>();
	private Size robotSize = new Size(10, 10);
	private Size fieldSize = new Size(600, 800);
	private Color itemColor = Color.black;
	private FieldMatrix fieldMatrix;
	
	@Before
    public void setUp() {
		this.view = new View(this.fieldSize, this.robotSize, this.itemColor);
		this.field = new Field(this.view, this.fieldSize, this.robotSize);
		
		this.itemA = new Line(new Position(10, 10), null);
		this.itemA.setSize(new Size(50, 0));
		this.items.add(itemA);
		this.field.addItem(itemA);
		
		this.itemB = new Line(new Position(50,70), null);
		this.itemB.setSize(new Size(0,50));
		this.items.add(itemB);
		this.field.addItem(itemB);
		
		this.finishPosition = new Position(100, 100);
		this.finish = new Finish(this.finishPosition, this.field);
		this.items.add(finish);
		this.field.addItem(finish);
		
		this.fieldMatrix = new FieldMatrix(this.fieldSize, new Size(10, 10), this.field);
    }

	/**
	 * setup with some contains
	 */
	@Test
	public void testContains() {
		//test item A
		assertEquals(1, this.fieldMatrix.contains(new Position(1,1)));
		assertEquals(1, this.fieldMatrix.contains(new Position(6,1)));
		
		//test itemB
		assertEquals(1, this.fieldMatrix.contains(new Position(5,7)));
		assertEquals(1, this.fieldMatrix.contains(new Position(5,12)));
		
		//test finish
		assertEquals(2, this.fieldMatrix.contains(new Position(10,10)));
		assertEquals(2, this.fieldMatrix.contains(new Position(11,11)));
	}
	
	/**
	 * test if constructor without field creates an Matrix with only 0
	 */
	@Test
	public void testCreteEmpty() {
		FieldMatrix fm = new FieldMatrix(new Size(50,50), new Size(10, 10));
		for(int i = 0; i<5;i++) {
			for(int j=0; j<5; j++) {
				assertEquals(0, fm.contains(new Position(i,j)));
			}
		}
		
	}
	
	/**
	 * test set value to field matrix
	 */
	@Test
	public void testSet() {
		FieldMatrix fm = new FieldMatrix(new Size(50,50), new Size(10, 10));
		fm.set(new Position(1,1), 5);
		assertEquals(5, fm.contains(new Position(1,1)));
		fm.set(new Position(2,2), 3);
		assertEquals(3, fm.contains(new Position(2,2)));
		
	}
}
