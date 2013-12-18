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
import frontend.impl.items.handler.LineEndHandler;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

/**
 * Created by ca-za on 10.12.13.
 */
public class LineEndHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position itemPosition = new Position(20, 50);
    private Position handlerPosition = new Position(20, 50);
    private Item robot;
    private Position robotPosition = new Position(70, 50);
    private Size robotSize = new Size(10, 10);
    private Size fieldSize = new Size(600, 800);
    private Size itemSize = new Size(10, 10);
    private Size handleSize = new Size(16, 16);
    private Color itemColor = Color.black;

    private ItemHandler handler;
    @Before
    public void setUp() {
        this.view = new View(this.fieldSize, this.robotSize, this.itemColor);
        this.field = new Field(this.view, this.fieldSize, this.robotSize);
        this.item = new Line(this.itemPosition, this.field);
        this.item.setSize(this.itemSize);
        this.field.addItem(item);
        this.handler = new LineEndHandler(this.item, this.handlerPosition, this.field);
        
        this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
        this.field.addItem(this.robot);
    }

    @Test
    public void testDragInteraction() {
        //set new Position
        this.handlerPosition = new Position(30, 60);
        //calculate origin Position
        Position originPosition = this.item.getPosition();
        //calculate new Size
        this.itemSize = new Size(this.itemPosition.getOriginX() - originPosition.getOriginX(),
                this.itemPosition.getOriginY() - originPosition.getOriginY());

        this.handler.dragInteraction(this.itemPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.item.getSize(), this.itemSize);
    }

    @Test
    public void testStopInteraction() {
        //stop Interaction does return resize because of Robot
    	this.handler.startInteraction(this.handlerPosition);
    	this.handler.dragInteraction(new Position(110, 50));
        this.handler.stopInteraction(new Position(110, 50));
        
        assertEquals(this.handlerPosition, this.handler.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        
        //stop Iteraction possible
        //set new Position
        this.handlerPosition = new Position(30, 60);
        //calculate origin Position
        Position originPosition = this.item.getPosition();
        //calculate new Size
        this.itemSize = new Size(this.itemPosition.getOriginX() - originPosition.getOriginX(),
                this.itemPosition.getOriginY() - originPosition.getOriginY());

        this.handler.stopInteraction(this.itemPosition);

        assertEquals(this.item.getPosition(), this.itemPosition);
        assertEquals(this.item.getSize(), this.itemSize);
    }

    @Test
    public void testGetOwner() {
        assertEquals(this.handler.getOwner(), this.item);
    }

    @Test
    public void testSetGetPosition() {
        assertEquals(this.handler.getPosition(), this.handlerPosition);

        //set new Position
        this.handlerPosition = new Position(70, 50);
        this.handler.setPosition(this.handlerPosition);
        assertEquals(this.handler.getPosition(), this.handlerPosition);
    }

    @Test
    public void testGetSize() {
        assertEquals(this.handler.getSize(), this.handleSize);
    }

    @Test
    public void testContains() {
        assertTrue(this.handler.contains(this.handlerPosition));
    }
}
