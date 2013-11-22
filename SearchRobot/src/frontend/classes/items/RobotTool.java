package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class RobotTool extends AbstractTool {
	private Size size;

	/**
	 * 
	 */
	private static final long serialVersionUID = 448110753972659981L;

	public RobotTool(Field field, Size s) {
		super(field);
		this.size = s;
	}

	private Item item;
	
	@Override
	public void mouseDown(Position p) {
		this.item = new Robot(p, size, field.getFieldSize());
		getField().addItem(this.item);
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
