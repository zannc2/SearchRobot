package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.classes.view.Field;
import frontend.interfaces.Item;


public class LineTool extends AbstractTool {

	public LineTool(Field field) {
		super(field);
	}

	private Item i;
	

	@Override
	public void mouseDown(Position p) {
		this.i = new Line(p);
		getField().addItem(this.i);
		//System.out.println("Line Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) {
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
//		System.out.println("X-Pos: " + actualX + " YPos: " + actualY + " height: " + i.getSize().getHeight() + "Width: " + i.getSize().getWidth());
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
	}

}
