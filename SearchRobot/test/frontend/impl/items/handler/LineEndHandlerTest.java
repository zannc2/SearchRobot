package frontend.impl.items.handler;

import frontend.impl.items.Line;
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
 * Created by ca-za on 10.12.13.
 */
public class LineEndHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position itemPosition = new Position(20, 50);
    private Position handlerPosition = new Position(20, 50);
    private Size robotSize = new Size(10, 10);
    private Size fieldSize = new Size(600, 800);
    private Size itemSize = new Size(0, 0);
    private Size handleSize = new Size(16, 16);
    private Color itemColor = Color.black;

    private ItemHandler handler;
    @Before
    public void setUp() {
        this.view = new ViewImpl(this.fieldSize, this.robotSize, this.itemColor);
        this.field = new Field(this.view, this.fieldSize, this.robotSize);
        this.item = new Line(this.itemPosition, this.field);

        this.handler = new LineEndHandler(this.item, this.handlerPosition, this.field);
    }

    @Test
    public void testGetCursor() {
        //assertEquals(this.handler.getCursor(), new Cursor(Cursor.N_RESIZE_CURSOR));
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
        assertEquals(this.handler.getPositioin(), this.handlerPosition);

        //set new Position
        this.handlerPosition = new Position(70, 50);
        this.handler.setPosition(this.handlerPosition);
        assertEquals(this.handler.getPositioin(), this.handlerPosition);
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