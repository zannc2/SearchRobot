package frontend.classes.items;

import java.awt.Cursor;
import java.util.List;

import helper.Position;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class FinishTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6250908523620856857L;


	public FinishTool(Field field) {
		super(field);
	}

	private Item i;


	@Override
	public void mouseDown(Position p) {
		List<Item> l = getField().getItems();

		for(int i = 0; i < l.size(); i++)
		{
			if(!l.get(i).contains(p))
			{
				this.i = new Finish(p);
				getField().addItem(this.i);
			}
		}
	}

	@Override
	public void mouseDrag(Position p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(Position p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}