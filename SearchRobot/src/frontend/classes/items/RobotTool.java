package frontend.classes.items;

import helper.Position;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class RobotTool extends AbstractTool {
	
	private Field field;

	/**
	 * 
	 */
	private static final long serialVersionUID = 448110753972659981L;

	public RobotTool(Field field) {
		super(field);
		this.field = field;
	}

	private Item i;
	
	@Override
	public void mouseDown(Position p) {
		this.i = new Robot(p);
		getField().addItem(this.i);
		System.out.println("Robot Created and added to View");
		this.field.setRoboterPosition(p);
	}

	@Override
	public void mouseDrag(Position p) {

	}

	@Override
	public void mouseUp(Position p) {

	}

	@Override
	public void mouseOver(Position p) {
		// Not needed
	}
}
