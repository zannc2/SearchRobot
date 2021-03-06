package frontend.impl.items;

import helper.Position;
import helper.Size;
import frontend.impl.view.Field;
import frontend.interfaces.Item;

/**
 * This is the class for the line tool, which creates the line items
 * It extends the {@link AbstractTool} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class LineTool extends AbstractTool {

	private static final long serialVersionUID = -6532113494936543327L;
	private Item item;

	/**
	 * Constructor sets the field in the super class
	 * 
	 * @param field the field which this tool belongs
	 */
	public LineTool(Field field) {
		super(field);
	}



	@Override
	public void mouseDown(Position p) {
		this.item = new Line(p, getField());
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
		if(!getField().checkIfPositionFree(item)) getField().removeItem(item);
	}

	@Override
	public void mouseOver(Position p) {
		// Not needed
	}

}
