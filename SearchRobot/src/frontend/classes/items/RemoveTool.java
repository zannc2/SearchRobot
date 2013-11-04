package frontend.classes.items;

import helper.Position;

import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class RemoveTool extends AbstractTool {


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
				System.out.println("Etwas Gelöscht");
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
		// Not needed
	}

}
