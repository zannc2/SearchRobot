package test.frontend.impl.items.selection;

import helper.Position;
import helper.Size;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Line;
import frontend.impl.items.selection.SelectionTool;
import frontend.impl.items.selection.SelectionToolState;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;

/**
 * Created by ca-za on 13.12.13.
 */
public class SelectionToolTest {

    private View view;
    private Field field;
    private SelectionTool selectionTool;
    private SelectionToolState state;
    private Position itemPosition;
    private Item item;

    private int stateCalled = 0;
    public class MockState extends SelectionToolState {

        protected MockState(SelectionTool context) {
            super(context);
        }

        @Override
        protected void mouseDownEvent(Position p) {
            stateCalled++;
        }
    }

    @Before
    public void setUp() {
        this.view = new View(new Size(600, 300), new Size(10, 10), Color.BLACK);
        this.field = new Field(this.view, new Size(600, 300), new Size(10, 10));
        this.selectionTool = new SelectionTool(this.field);
        this.state = new MockState(this.selectionTool);

        this.itemPosition = new Position(30, 30);
        this.item = new Line(this.itemPosition, this.field);
        this.field.addItem(this.item);
    }

//    @Test
//    public void testSetToolState() {
//        this.selectionTool.setToolState(this.state);
//        this.selectionTool.mouseDown(new Position(10, 10));
//        assertEquals(1, this.stateCalled);
//        this.stateCalled = 0;
//    }
//
//    @Test
//    public void testGetStateFactory() {
//        assertTrue(this.selectionTool.getStateFactory() instanceof MyStateFactory);
//    }
//
//    @Test
//    public void testGetItemByPosition() {
//        assertEquals(this.item, this.selectionTool.getItemByPosition(this.itemPosition));
//    }

    @Test
    public void testGetSelection() {

    }

    @Test
    public void testDoAdjustSelections() {

    }

    @Test
    public void testClearSelection() {

    }

    @Test
    public void testDoSetSelectionAreaTo() {

    }

    @Test
    public void testDoDiscardSelectionArea() {

    }

    @Test
    public void testDoInitSelectionArea() {

    }

    @Test
    public void testGetCurrentHandle() {

    }

    @Test
    public void testSetCurrentHandle() {

    }

    @Test
    public void testDoAddToSelection() {

    }

    @Test
    public void testDoSetMoveCursor() {

    }

    @Test
    public void testDoSetDefaultCursor() {

    }

    @Test
    public void testDoSetItemeHandleCursor() {

    }

    @Test
    public void testGetItemHandlerByPosition() {

    }

    @Test
    public void testIsOnItemHandle() {

    }

    @Test
    public void testIsOnUnselectedItem() {

    }

    @Test
    public void testIsOnSelectedItem() {

    }

    @Test
    public void testIsOnEmptyArea() {

    }

    @Test
    public void testGetPreviousMouseDragPosition() {

    }

    @Test
    public void testDoMoveSelectedItems() {

    }

    @Test
    public void testEndMoveSelectedItems() {

    }
}
