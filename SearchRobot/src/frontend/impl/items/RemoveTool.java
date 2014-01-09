package frontend.impl.items;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;

import java.awt.Cursor;
import java.util.List;

/**
 * This is the class for the remove tool, which can remove items
 * It extends the {@link AbstractTool} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class RemoveTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615825827692046264L;

	/**
	 * Constructor sets the field in the super class
	 * 
	 * @param field the field which this tool belongs
	 */
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
		// not needed
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
