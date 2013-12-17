package robot.impl;

import frontend.impl.SearchRobotEditor;
import frontend.impl.items.Robot;
import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ca-za on 11.12.13.
 */
public class RobotControllerTest {
    private RobotController robotController;
    private SearchRobotEditor editor;
    private Field field;
    private int robotSpeed;
    private Size fieldSize = new Size(600, 500);
    private Size robotSize = new Size(10, 10);
    private Item robot;

    @Before
    public void setUp() {
        this.robotSpeed = 200;
        this.editor = new SearchRobotEditor();
        this.field = new Field(this.fieldSize, this.robotSize);
        this.robot = new Robot(new Position(20, 40), this.robotSize, this.field);
        this.field.addItem(robot);

        this.robotController = new RobotController(this.editor, this.field, this.robotSpeed);
    }

    @Test
    public void testFinished() {
        this.robotController.setFinished(true);
        assertTrue(this.robotController.isFinished());
        this.robotController.setFinished(false);
        assertFalse(this.robotController.isFinished());
    }

    @Test
    public void testUnreachable() {
        this.robotController.setUnreachable(true);
        assertTrue(this.robotController.isUnreachable());
        this.robotController.setUnreachable(false);
        assertFalse(this.robotController.isUnreachable());
    }
}
