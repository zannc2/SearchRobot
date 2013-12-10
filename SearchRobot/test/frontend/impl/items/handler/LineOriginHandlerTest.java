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
public class LineOriginHandlerTest {
    private View view;
    private Field field;
    private Item item;
    private Position position = new Position(20, 50);
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
        this.item = new Line(this.position, this.field);

        this.handler = new LineOriginHandler(this.item, this.position, this.field);
    }

    @Test
    public void testGetCursor() {
        assertEquals(this.handler.getCursor(), new Cursor(Cursor.N_RESIZE_CURSOR));
    }

    @Test
    public void testDragInteraction() {
        //set new Position
        this.position = new Position(30, 60);
        //calculate end Position
        Position endPosition = new Position(this.position.getOriginX() + this.itemSize.getWidth(),
                this.position.getOriginY() + this.itemSize.getHeight());
        //calculate new Size
        this.itemSize = new Size(endPosition.getOriginX() - this.position.getOriginX(),
                endPosition.getOriginY() - this.position.getOriginY());

        this.handler.dragInteraction(this.position);

        assertEquals(this.position, this.item.getPosition());
        assertEquals(this.item.getSize(), this.itemSize);
    }

    @Test
    public void testStopInteraction() {
        //set new Position
        this.position = new Position(30, 60);
        //calculate end Position
        Position endPosition = new Position(this.position.getOriginX() + this.itemSize.getWidth(),
                this.position.getOriginY() + this.itemSize.getHeight());
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
        assertEquals(this.handler.getPositioin(), this.position);

        //set new Position
        this.position = new Position(70, 50);
        this.handler.setPosition(this.position);
        assertEquals(this.handler.getPositioin(), this.position);
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
