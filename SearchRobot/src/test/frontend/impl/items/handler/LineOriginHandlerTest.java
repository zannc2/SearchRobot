package test.frontend.impl.items.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helper.Position;
import helper.Size;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Line;
import frontend.impl.items.Robot;
import frontend.impl.items.handler.LineOriginHandler;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

/**
 * Created by ca-za on 10.12.13.
 */
public class LineOriginHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position position = new Position(20, 50);
    private Item robot;
    private Position robotPosition = new Position(70, 50);
    private Size robotSize = new Size(10, 10);
    private Size fieldSize = new Size(600, 800);
    private Size itemSize = new Size(0, 0);
    private Size handleSize = new Size(16, 16);
    private Color itemColor = Color.black;

    private ItemHandler handler;

    @Before
    public void setUp() {
        this.view = new View(this.fieldSize, this.robotSize, this.itemColor);
        this.field = new Field(this.view, this.fieldSize, this.robotSize);
        this.item = new Line(this.position, this.field);
        this.item.setSize(itemSize);
        this.field.addItem(item);

        this.handler = new LineOriginHandler(this.item, this.position, this.field);
        
        this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
        this.field.addItem(this.robot);
    }

    @Test
    public void testDragInteraction() {
        //set new Position
        this.position = new Position(30, 60);
        //calculate end Position
        Position endPosition = new Position(
				this.item.getPosition().getOriginX() + this.item.getSize().getWidth(),
				this.item.getPosition().getOriginY() + this.item.getSize().getHeight());
        //calculate new Size
        int width = endPosition.getOriginX() - this.position.getOriginX();
		int height = endPosition.getOriginY() - this.position.getOriginY();
        this.itemSize = new Size(width, height);

        this.handler.dragInteraction(this.position);

        assertEquals(this.position, this.item.getPosition());
        assertEquals(this.item.getSize(), this.itemSize);
    }

    @Test
    public void testStopInteraction() {
        //stop Interaction does return resize
    	this.handler.startInteraction(this.position);
    	this.handler.dragInteraction(new Position(110, 50));
        this.handler.stopInteraction(new Position(110, 50));
        
        assertEquals(this.position, this.handler.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        
        //set new Position
        this.position = new Position(30, 60);
        //calculate end Position
        Position endPosition = new Position(
				this.item.getPosition().getOriginX() + this.item.getSize().getWidth(),
				this.item.getPosition().getOriginY() + this.item.getSize().getHeight());
        //calculate new Size
        this.itemSize = new Size(endPosition.getOriginX() - this.position.getOriginX(),
                endPosition.getOriginY() - this.position.getOriginY());

        this.handler.stopInteraction(this.position);

        assertEquals(this.item.getPosition(), this.position);
        assertEquals(this.item.getSize(), this.itemSize);
    }

    @Test
    public void testGetOwner() {
        assertEquals(this.handler.getOwner(), this.item);
    }

    @Test
    public void testSetGetPosition() {
        assertEquals(this.handler.getPosition(), this.position);

        //set new Position
        this.position = new Position(70, 50);
        this.handler.setPosition(this.position);
        assertEquals(this.handler.getPosition(), this.position);
    }

    @Test
    public void testGetSize() {
        assertEquals(this.handler.getSize(), this.handleSize);
    }

    @Test
    public void testContains() {
        assertTrue(this.handler.contains(this.position));
    }
}
