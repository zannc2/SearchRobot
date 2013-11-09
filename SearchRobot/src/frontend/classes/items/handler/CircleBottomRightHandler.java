package frontend.classes.items.handler;

import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;
import java.awt.Rectangle;

public class CircleBottomRightHandler extends AbstractHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7085318201596784835L;
	private Position position;
	private Item owner;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.SE_RESIZE_CURSOR);
	
	public CircleBottomRightHandler(Item owner, Position position) {
		super(owner, position);
		this.owner = owner;
		this.position = position;
	}

	@Override
	public Cursor getCursor() {
		return RESIZE_CURSOR;
	}

	@Override
	public void startInteraction(Position p) {
	}

	@Override
	public void dragInteraction(Position p) {
		// get origin Coordinates
		Size origS = this.owner.getSize();
		Position origP = this.owner.getPosition();

		//get bottomLeft Position
		Position topLeft = origP;

		// calculate new Size
		int width = p.getOriginX() - topLeft.getOriginX();
		int height = p.getOriginY() - topLeft.getOriginY();

		Position newOrigin = null;
		Size newS = null;
		if(width > height) {
			newOrigin = topLeft;
			newS = new Size(width, width);
		}
		else {
			newOrigin = topLeft;	
			newS = new Size(height, height);
		}

		this.position = newOrigin;
		java.awt.Rectangle r = getAWTRectangle(newS.getWidth(), newS.getHeight());

		//change Position and Size
		Position newP = new Position((int) r.getX(), (int) r.getY());
		this.position = newP;
		super.setPosition(newP);
		this.owner.setPosition(newP);
		this.owner.setSize(new Size((int) r.getWidth(), (int) r.getHeight()));
	}

	@Override
	public void stopInteraction(Position p) {
		this.dragInteraction(p);
	}
	
	private Rectangle getAWTRectangle(int w, int h) {
		Rectangle r = null;
		int width = w;
		int height = h;
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX() - height, this.position
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX() + height, this.position
							.getOriginY()
							+ height, -height, -height);
				}
			}
		}
		return r;
	}

}
