package frontend.classes.items;

import helper.Position;
import helper.Vector;

import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class SelectionTool implements Tool {

	private View view;
	private Item item;
	Position lastP;
	
	public SelectionTool(View view) {
		this.view = view;
	}

	public void mouseDown(Position p) 
	{

		List<Item> l = getView().getItems();
		
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

	public View getView() {
		return view;
	}	

}