package test.frontend.impl.items.selection;

import frontend.impl.items.selection.SelectionTool;
import frontend.impl.items.selection.SelectionToolState;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import org.junit.Test;

/**
 * Created by ca-za on 13.12.13.
 */
public class InitTest {
    private Size fieldSize = new Size(600, 400);
    private Size robotSize = new Size(10, 10);
    private View view;
    private Field field;
    private SelectionTool selectionTool;
    private Position itemPosition;
    private Item item;

    private SelectionToolState state;

//    @Before
//    public void setUp() {
//        this.view = new ViewImpl(this.fieldSize, this.robotSize, Color.BLACK);
//        this.field = new Field(this.view, this.fieldSize, this.robotSize);
//
//        this.item = new Line(this.itemPosition, this.field);
//        this.field.addItem(this.item);
//
//        this.selectionTool = new SelectionTool(this.field);
//        this.state = new Init(this.selectionTool);
//    }
//
//    @Test
//    public void testMouseDownEvent() {
//        //on unselected Item
//        this.state.mouseDown(this.itemPosition);
//    }

    @Test
    public void testMouseOverEvent() {

    }
}
