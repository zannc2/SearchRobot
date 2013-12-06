package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.awt.Rectangle;
import java.util.List;

import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class CircleTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -994596525815413661L;

	public CircleTool(Field field) {
		super(field);
		this.field = field;
	}

	private Field field;
	private Item item;

	@Override
	public void mouseDown(Position p) {
		this.item = new Circle(p, this.field);
		getField().addItem(this.item);
		//System.out.println("Circle Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) 
	{
		int actualX = item.getPosition().getOriginX();
		int actualY = item.getPosition().getOriginY();
		item.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
	}
	
	private Rectangle getAWTRectangle() {
		Rectangle r = null;
		int width = item.getSize().getWidth();
		int height = item.getSize().getHeight();
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(item.getPosition().getOriginX(), item.getPosition()
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(item.getPosition().getOriginX(), item.getPosition()
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(item.getPosition().getOriginX(), item.getPosition()
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(item.getPosition().getOriginX(), item.getPosition()
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(item.getPosition().getOriginX() + width, item.getPosition()
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(item.getPosition().getOriginX() - height, item.getPosition()
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(item.getPosition().getOriginX() + width, item.getPosition()
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(item.getPosition().getOriginX() + height, item.getPosition()
							.getOriginY()
							+ height, -height, -height);
				}
			}
		}
		return r;
	}

	@Override
	public void mouseUp(Position p) {
		
		Rectangle r = getAWTRectangle();
		item.setPosition(new Position((int)r.getX(), (int)r.getY()));
		item.setSize(new Size((int)r.getWidth(), (int)r.getHeight()));
		if(!field.checkIfPositionFree(item)) field.removeItem(item);
	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}
