package frontend.impl.items;

import helper.Position;
import helper.Size;

import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;


public class LineTool extends AbstractTool {

	private Item item;
	private Field field;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3403869222008259766L;


	public LineTool(Field field) {
		super(field);
		this.field = field;
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
//		System.out.println("X-Pos: " + actualX + " YPos: " + actualY + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
		if(!field.checkIfPositionFree(item)) field.removeItem(item);
	}
		
	@Override
	public void mouseOver(Position p) {
		// Not needed
	}

}