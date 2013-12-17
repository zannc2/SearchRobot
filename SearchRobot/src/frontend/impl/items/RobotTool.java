package frontend.impl.items;

import java.util.List;

import helper.Position;
import helper.Size;
import frontend.impl.view.Field;
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

		List<Item> l = getField().getItems();

		boolean newItem = true;
		for(int i = 0; i < l.size(); i++){
			if(l.get(i) instanceof Robot) newItem = false;
		}

		if(newItem)
		{
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-10 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-10)
			{
				this.item = new Robot(newP, size, field);
				getField().addItem(this.item); 
				
				if(!field.checkIfPositionFree(item)) 
				{
					field.removeItem(item);
					this.item = null;
				}
			}
		}
		else
		{
			Position oldP = item.getPosition();
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-10 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-10)
			{
				item.setPosition(newP);
				
				if(!field.checkIfPositionFree(item)) 
				{
					item.setPosition(oldP);
				}
			}
		}
	}

	@Override
	public void mouseDrag(Position p) {
//
//		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
//		if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-10 && newP.getOriginY() 
//				<= field.getFieldSize().getHeight()-10)
//		{
//			item.setPosition(newP);
//		}
	}

	@Override
	public void mouseUp(Position p) {
//		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
//		if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-10 && newP.getOriginY() 
//				<= field.getFieldSize().getHeight()-10)
//		{
//			item.setPosition(newP);
//		}
//
//		//chech position
//		if(!field.checkIfPositionFree(item)) getField().removeItem(item);
	}

	@Override
	public void mouseOver(Position p) {
		// Not needed
	}
}
