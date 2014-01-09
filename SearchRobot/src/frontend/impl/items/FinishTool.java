package frontend.impl.items;

import helper.Position;

import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;

/**
 * This is the class for the robot tool, which creates the finish item
 * It extends the {@link AbstractTool} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class FinishTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4987041765338470637L;

	/**
	 * Constructor sets the field in the super class
	 * 
	 * @param field the field which this tool belongs
	 */
	public FinishTool(Field field) {
		super(field);
	}

	private Item item;


	@Override
	public void mouseDown(Position p) {
		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);

		List<Item> l = getField().getItems();

		boolean newItem = true;
		for(int i = 0; i < l.size(); i++){
			if(l.get(i) instanceof Finish) 
			{
				newItem = false;
				this.item = l.get(i);
			}
		}

		if(newItem)
		{
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= getField().getFieldSize().getWidth()-20 && newP.getOriginY() 
					<= getField().getFieldSize().getHeight()-20){
				this.item = new Finish(newP, getField());
				getField().addItem(this.item);

				if(!getField().checkIfPositionFree(this.item)) 
				{
					getField().removeItem(this.item);
					this.item = null;
				}
			}
		}
		else
		{
			Position oldP = this.item.getPosition();
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= getField().getFieldSize().getWidth()-20 && newP.getOriginY() 
					<= getField().getFieldSize().getHeight()-20){
				this.item.setPosition(newP);

				if(!getField().checkIfPositionFree(this.item)) 
				{
					this.item.setPosition(oldP);
				}
			}
		}


	}

	@Override
	public void mouseDrag(Position p) {
		//not implemented
	}

	@Override
	public void mouseUp(Position p) {
		//not implemented
	}

	@Override
	public void mouseOver(Position p) {
		//not implemented
	}
}