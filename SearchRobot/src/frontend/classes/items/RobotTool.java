package frontend.classes.items;

import java.util.List;

import helper.Position;
import helper.Size;
import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class RobotTool extends AbstractTool {
	private Size size;

	/**
	 * 
	 */
	private static final long serialVersionUID = 448110753972659981L;

	public RobotTool(Field field, Size s) {
		super(field);
		this.size = s;
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
			if(i.contains(newP) || i.contains(new Position(newP.getOriginX() + size.getWidth(), newP.getOriginY())) ||
					i.contains(new Position(newP.getOriginX(), newP.getOriginY() + size.getHeight())) ||
					i.contains(new Position(newP.getOriginX() + size.getWidth(), newP.getOriginY() + size.getHeight())))
			{
				draw = false;
			}
		}

		if(draw)
		{
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-10 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-10)
			{
				this.item = new Robot(newP, size, field);
				getField().addItem(this.item); 
			}
		} 
		this.item = new Robot(p, size, field);
		getField().addItem(this.item); 
	}

	@Override
	public void mouseDrag(Position p) {

	}

	@Override
	public void mouseUp(Position p) {

	}

	@Override
	public void mouseOver(Position p) {
		// Not needed
	}
}
