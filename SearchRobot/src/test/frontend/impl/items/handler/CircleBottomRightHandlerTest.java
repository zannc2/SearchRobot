package test.frontend.impl.items.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helper.Position;
import helper.Size;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Circle;
import frontend.impl.items.Robot;
import frontend.impl.items.handler.CircleBottomRightHandler;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

/**
 * Created by ca-za on 11.12.13.
 */
public class CircleBottomRightHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position itemPosition = new Position(20, 50);
    private Position handlerPosition = new Position(30, 60);
    private Item robot;
    private Position robotPosition = new Position(40,70);
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
        this.item = new Circle(this.itemPosition, this.field);
        this.item.setSize(this.itemSize);
        this.field.addItem(item);
        
        this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
        this.field.addItem(this.robot);

        this.handler = new CircleBottomRightHandler(this.item, this.handlerPosition, this.field);
    }

    @Test
    public void testDragInteraction() {
        this.handlerPosition = new Position(40, 70);
        //calculate new Size
        int width = this.handlerPosition.getOriginX() - this.itemPosition.getOriginX();
        int height = this.handlerPosition.getOriginY() - this.itemPosition.getOriginY();
        this.itemSize = new Size(width, height);

        this.handler.dragInteraction(this.handlerPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        assertEquals(this.handlerPosition, this.handler.getPosition());
    }

    @Test
    public void testStopInteraction() {
        this.handlerPosition = new Position(30, 60);
        //calculate new Size
        int width = this.handlerPosition.getOriginX() - this.itemPosition.getOriginX();
        int height = this.handlerPosition.getOriginY() - this.itemPosition.getOriginY();
        this.itemSize = new Size(width, height);

        this.handler.stopInteraction(this.handlerPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        assertEquals(this.handlerPosition, this.handler.getPosition());

        //stop Iteraction not Posible because Item over Robot
        this.handler.startInteraction(this.handlerPosition);
        this.handler.dragInteraction(new Position(50, 80));
        this.handler.stopInteraction(new Position(50, 80));
        
        assertEquals(this.handlerPosition, this.handler.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
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
