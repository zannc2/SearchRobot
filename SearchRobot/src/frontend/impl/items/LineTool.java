package frontend.impl.items;

import helper.Position;
import helper.Size;
import frontend.impl.view.Field;
import frontend.interfaces.Item;


public class LineTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403869222008259766L;

	private Item item;

	public LineTool(Field field) {
		super(field);
	}



	@Override
	public void mouseDown(Position p) {
		this.item = new Line(p, this.field);
		getField().addItem(this.item);
		//System.out.println("Line Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {
		int actualX = item.getPosition().getOriginX();
		int actualY = item.getPosition().getOriginY();

		item.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
		if(!field.checkIfPositionFree(item)) field.removeItem(item);
	}

	@Override
	public void mouseOver(Position p) {
		// Not needed
	}

}
