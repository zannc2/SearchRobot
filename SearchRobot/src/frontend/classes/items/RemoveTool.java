package frontend.classes.items;

import helper.Position;

import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.Tool;
import frontend.interfaces.View;

public class RemoveTool implements Tool {

	private View view;
	
	public RemoveTool(View view) {
		this.view = view;
	}

	public void mouseDown(Position p) 
	{
		List<Item> l = getView().getItems();
		
		for(int i = 0; i < l.size(); i++)
		{
			if(l.get(i).contains(p))
			{
				getView().removeItem(l.get(i));
				System.out.println("Etwas Gelöscht");
			}
		}
		
	}

	public void mouseDrag(Position p) {

	}

	public void mouseUp(Position p) {

	}

	public View getView() {
		return view;
	}	

}
