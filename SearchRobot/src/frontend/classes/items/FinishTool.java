package frontend.classes.items;

import helper.Position;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class FinishTool extends AbstractTool {

	public FinishTool(Field field) {
		super(field);
	}

	private Item i;
	

	@Override
	public void mouseDown(Position p) {
		this.i = new Finish(p);
		getField().addItem(this.i);
	}

	@Override
	public void mouseDrag(Position p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(Position p) {
		// TODO Auto-generated method stub
		
	}
}