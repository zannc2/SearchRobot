package frontend.classes.items;

import helper.Position;
import helper.Size;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class CircleTool extends AbstractTool {

	public CircleTool(Field field) {
		super(field);
	}

	private Item i;

	@Override
	public void mouseDown(Position p) {
		this.i = new Circle(p);
		getField().addItem(this.i);
		//System.out.println("Circle Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) 
	{
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();

		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
	}

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}	
}
