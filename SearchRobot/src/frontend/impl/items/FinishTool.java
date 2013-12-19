package frontend.impl.items;

import helper.Position;

import java.util.List;

import frontend.impl.view.Field;
import frontend.interfaces.Item;

public class FinishTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4987041765338470637L;

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
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-20 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-20){
				this.item = new Finish(newP, field);
				getField().addItem(this.item);

				if(!field.checkIfPositionFree(this.item)) 
				{
					field.removeItem(this.item);
					this.item = null;
				}
			}
		}
		else
		{
			Position oldP = this.item.getPosition();
			if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-20 && newP.getOriginY() 
					<= field.getFieldSize().getHeight()-20){
				this.item.setPosition(newP);

				if(!field.checkIfPositionFree(this.item)) 
				{
					this.item.setPosition(oldP);
				}
			}
		}


	}

	@Override
	public void mouseDrag(Position p) {
		//		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
		//		if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-20 && newP.getOriginY() 
		//				<= field.getFieldSize().getHeight()-20){
		//			item.setPosition(newP);
		//		}
	}

	@Override
	public void mouseUp(Position p) {
		//		Position newP = new Position((p.getOriginX()/10)*10, (p.getOriginY()/10)*10);
		//		if(newP.getOriginX()>=0 && newP.getOriginY() >= 0 && newP.getOriginX() <= field.getFieldSize().getWidth()-20 && newP.getOriginY() 
		//				<= field.getFieldSize().getHeight()-20){
		//			item.setPosition(newP);
		//		}
		//
		//		if(!field.checkIfPositionFree(item)) field.removeItem(item);

	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}