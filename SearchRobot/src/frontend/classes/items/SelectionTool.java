package frontend.classes.items;

import helper.Position;
import helper.Vector;

import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class SelectionTool extends AbstractTool {

	public SelectionTool(Field field) {
		super(field);
	}

	private Item item;
	Position lastP;

	public void mouseDown(Position p) 
	{

		List<Item> l = getField().getItems();
		
		for(int i = 0; i < l.size(); i++)
		{
			if(l.get(i).contains(p))
			{
				item = l.get(i);
			}
		}
		
		lastP = p;
	}

	public void mouseDrag(Position p) {
		if(item != null)
		{
			Position calc = new Position(p.getOriginX()-lastP.getOriginX(), p.getOriginY()-lastP.getOriginY());
			item.move(new Vector(calc));
		}
		lastP = p;
			
	}

	public void mouseUp(Position p) {
		item = null;
	}

}