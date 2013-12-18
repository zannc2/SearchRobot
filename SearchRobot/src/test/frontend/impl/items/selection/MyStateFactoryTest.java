package test.frontend.impl.items.selection;

import static org.junit.Assert.assertTrue;
import frontend.impl.items.selection.DragArea;
import frontend.impl.items.selection.DragHandle;
import frontend.impl.items.selection.Init;
import frontend.impl.items.selection.Moving;
import frontend.impl.items.selection.MyStateFactory;
import frontend.impl.items.selection.SelectionTool;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.StateFactory;
import helper.Size;

import java.awt.Color;

import org.junit.Test;

/**
 * Created by ca-za on 13.12.13.
 */
public class MyStateFactoryTest {
    private View view = new View(new Size(600, 300), new Size(10, 10), Color.BLACK);
    private Field field = new Field(this.view, new Size(600, 300), new Size(10, 10));
    private StateFactory myStateFactory = new MyStateFactory();
    private SelectionTool selectionTool = new SelectionTool(this.field);


    @Test
    public void testCreateDragAreaState() throws Exception {
        System.out.println(this.myStateFactory.createDragAreaState(this.selectionTool));
        assertTrue(this.myStateFactory.createDragAreaState(this.selectionTool) instanceof DragArea);
    }

    @Test
    public void testCreateDragHandleState() throws Exception {
        assertTrue(this.myStateFactory.createDragHandleState(this.selectionTool) instanceof DragHandle);
    }

    @Test
    public void testCreateInitState() throws Exception {
        assertTrue(this.myStateFactory.createInitState(this.selectionTool) instanceof Init);
    }

    @Test
    public void testCreateMovingState() throws Exception {
        assertTrue(this.myStateFactory.createMovingState(this.selectionTool) instanceof Moving);
    }
}
