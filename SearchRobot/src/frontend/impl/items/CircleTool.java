package frontend.impl.items;

import helper.Position;
import helper.Size;

import java.awt.Rectangle;

import frontend.impl.view.Field;
import frontend.interfaces.Item;

/**
 * This is the class for the circle tool, which creates the circle items
 * It extends the {@link AbstractTool} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class CircleTool extends AbstractTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764244882541005519L;

	/**
	 * Constructor sets the field in the super class
	 * 
	 * @param field the field which this tool belongs
	 */
	public CircleTool(Field field) {
		super(field);
	}

	private Item item;

	@Override
	public void mouseDown(Position p) {
		this.item = new Circle(p, getField());
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

	@Override
	public void mouseUp(Position p) {
		mouseDrag(p);
		Rectangle r = Size.getAWTRectangle(item.getSize(), item.getPosition());
		item.setPosition(new Position((int)r.getX(), (int)r.getY()));
		item.setSize(new Size((int)r.getWidth(), (int)r.getHeight()));
		if(!getField().checkIfPositionFree(item)) getField().removeItem(item);
	}

	@Override
	public void mouseOver(Position p) {
		//not needed
	}
}
