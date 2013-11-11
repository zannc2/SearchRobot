package frontend.classes.items;

import helper.Position;
import helper.Size;

import java.awt.Rectangle;

import frontend.classes.view.Field;
import frontend.interfaces.Item;

public class CircleTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -994596525815413661L;

	public CircleTool(Field field) {
		super(field);
	}

	private Item i;

	@Override
	public void mouseDown(Position p) {
		this.i = new Circle(p);
		getField().addItem(this.i);
		//System.out.println("Circle Created and added to View");
	}

	@Override
	public void mouseDrag(Position p) 
	{
		int actualX = i.getPosition().getOriginX();
		int actualY = i.getPosition().getOriginY();
		i.setSize(new Size(p.getOriginX() - actualX, p.getOriginY() -  actualY));
	}
	
	private Rectangle getAWTRectangle() {
		Rectangle r = null;
		int width = i.getSize().getWidth();
		int height = i.getSize().getHeight();
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX(), i.getPosition()
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(i.getPosition().getOriginX() + width, i.getPosition()
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX() - height, i.getPosition()
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(i.getPosition().getOriginX() + width, i.getPosition()
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(i.getPosition().getOriginX() + height, i.getPosition()
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
		i.setPosition(new Position((int)r.getX(), (int)r.getY()));
		i.setSize(new Size((int)r.getWidth(), (int)r.getHeight()));
	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}
