package frontend.impl.items;

import helper.Position;

import java.awt.Cursor;
import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;

public class RemoveTool extends AbstractTool {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7187559079694994595L;

	public RemoveTool(Field field) {
		super(field);
	}

	@Override
	public void mouseDown(Position p) 
	{
		List<Item> l = getField().getItems();

		for(int i = 0; i < l.size(); i++)
		{
			if(l.get(i).contains(p))
			{
				getField().removeItem(l.get(i));
				getField().getView().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	@Override
	public void mouseDrag(Position p) {

	}

	@Override
	public void mouseUp(Position p) {
		// not needed
	}

	@Override
	public void mouseOver(Position p) {
		List<Item> l = getField().getItems();

		boolean over = false;
		
		for(int i = 0; i < l.size(); i++)
		{
			if(l.get(i).contains(p))
			{
				over = true;
			}
		}
		
		if(over)
		{
			getField().getView().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else
		{
			getField().getView().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
