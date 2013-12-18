package test.frontend.impl.items.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helper.Position;
import helper.Size;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Circle;
import frontend.impl.items.handler.CircleTopLeftHandler;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

/**
 * Created by ca-za on 11.12.13.
 */
public class CircleTopLeftHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position itemPosition = new Position(20, 50);
    private Position handlerPosition = new Position(20, 50);
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

        this.handler = new CircleTopLeftHandler(this.item, this.handlerPosition, this.field);
    }

    @Test
    public void testDragInteraction() {
        this.handlerPosition = new Position(10, 40);
        //calculate bottomRight Position
        Position bottomRight = new Position(this.item.getPosition().getOriginX() + this.itemSize.getWidth(),
                this.item.getPosition().getOriginY() + this.itemSize.getHeight());

        //calculate new Size
        this.itemSize = new Size(bottomRight.getOriginX() - this.handlerPosition.getOriginX(),
                bottomRight.getOriginY() - this.handlerPosition.getOriginY());

        //set new Item Position
        this.itemPosition = this.handlerPosition;

        this.handler.dragInteraction(this.handlerPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        assertEquals(this.handlerPosition, this.handler.getPosition());
    }

    @Test
    public void testStopInteraction() {
        this.handlerPosition = new Position(20, 50);
        //calculate bottomRight Position
        Position bottomRight = new Position(this.item.getPosition().getOriginX() + this.itemSize.getWidth(),
                this.item.getPosition().getOriginY() + this.itemSize.getHeight());

        //calculate new Size
        this.itemSize = new Size(bottomRight.getOriginX() - this.handlerPosition.getOriginX(),
                bottomRight.getOriginY() - this.handlerPosition.getOriginY());

        //set new Item Position
        this.itemPosition = this.handlerPosition;

        this.handler.stopInteraction(this.handlerPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        assertEquals(this.handlerPosition, this.handler.getPosition());
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
