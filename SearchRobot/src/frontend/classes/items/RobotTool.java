package frontend.classes.items;

import helper.Position;
import frontend.classes.view.Field;
import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class RobotTool extends AbstractTool {

	public RobotTool(Field field) {
		super(field);
	}

	private Item i;
	
	@Override
	public void mouseDown(Position p) {
		this.i = new Robot(p);
		getField().addItem(this.i);
		System.out.println("Robot Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {

	}

	@Override
	public void mouseUp(Position p) {

	}
}
