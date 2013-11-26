package frontend.classes.items;

import helper.Position;

import java.util.List;

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

	private Item item;


	@Override
	public void mouseDown(Position p) {
		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
		List<Item> items = field.getItems();
		boolean draw = true;
		for(int j = 0; j < items.size(); j++)
		{
			Item i = items.get(j);
			if(i.contains(p) || i.contains(new Position(newP.getOriginX() + 20, newP.getOriginY())) ||
					i.contains(new Position(newP.getOriginX(), newP.getOriginY() + 20)) ||
					i.contains(new Position(newP.getOriginX() + 20, newP.getOriginY() + 20)) ||
					i.contains(new Position(newP.getOriginX() + 10, newP.getOriginY())) ||
					i.contains(new Position(newP.getOriginX() + 10, newP.getOriginY() + 20)) ||
					i.contains(new Position(newP.getOriginX(), newP.getOriginY() + 10)) ||
					i.contains(new Position(newP.getOriginX() + 10, newP.getOriginY() + 10)) ||
					i.contains(new Position(newP.getOriginX() + 20, newP.getOriginY() + 10)))
			{
				draw = false;
			}
		}

		if(draw)
		{
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-20 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-20){
				this.item = new Finish(newP, field);
				getField().addItem(this.item);
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