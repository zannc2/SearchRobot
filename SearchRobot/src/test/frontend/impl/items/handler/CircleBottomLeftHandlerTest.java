package test.frontend.impl.items.handler;

import frontend.impl.items.Circle;
import frontend.impl.items.handler.CircleBottomLeftHandler;
import frontend.impl.view.Field;
import frontend.impl.view.ViewImpl;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;
import frontend.interfaces.View;
import helper.Position;
import helper.Size;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ca-za on 11.12.13.
 */
public class CircleBottomLeftHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position itemPosition = new Position(20, 50);
    private Position handlerPosition = new Position(20, 60);
    private Size robotSize = new Size(10, 10);
    private Size fieldSize = new Size(600, 800);
    private Size itemSize = new Size(10, 10);
    private Size handleSize = new Size(16, 16);
    private Color itemColor = Color.black;

    private ItemHandler handler;

    @Before
    public void setUp() {
        this.view = new ViewImpl(this.fieldSize, this.robotSize, this.itemColor);
        this.field = new Field(this.view, this.fieldSize, this.robotSize);
        this.item = new Circle(this.itemPosition, this.field);
        this.item.setSize(this.itemSize);

        this.handler = new CircleBottomLeftHandler(this.item, this.handlerPosition, this.field);
    }

    @Test
    public void testDragInteraction() {
        this.handlerPosition = new Position(10, 70);
        //calculate topRight Position
        Position topRight = new Position(this.itemPosition.getOriginX() + this.itemSize.getWidth(),
                this.itemPosition.getOriginY());
        //calculate new Size
        int width = topRight.getOriginX() - this.handlerPosition.getOriginX();
        int height = this.handlerPosition.getOriginY() - topRight.getOriginY();
        this.itemSize = new Size(width, height);

        //set new Item Position
        this.itemPosition = new Position(this.handlerPosition.getOriginX(), this.itemPosition.getOriginY());

        this.handler.dragInteraction(this.handlerPosition);

        assertEquals(this.itemPosition, this.item.getPosition());
        assertEquals(this.itemSize, this.item.getSize());
        assertEquals(this.handlerPosition, this.handler.getPosition());

    }

    @Test
    public void testStopInteraction() {
        this.handlerPosition = new Position(20, 60);
        //calculate topRight Position
        Position topRight = new Position(this.itemPosition.getOriginX() + this.itemSize.getWidth(),
                this.itemPosition.getOriginY());
        //calculate new Size
        int width = topRight.getOriginX() - this.handlerPosition.getOriginX();
        int height = this.handlerPosition.getOriginY() - topRight.getOriginY();
        this.itemSize = new Size(width, height);

        //set new Item Position
        this.itemPosition = new Position(this.handlerPosition.getOriginX(), this.itemPosition.getOriginY());

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
