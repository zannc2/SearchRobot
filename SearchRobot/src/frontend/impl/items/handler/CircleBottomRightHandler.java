package frontend.impl.items.handler;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;
import java.awt.Rectangle;

public class CircleBottomRightHandler extends AbstractHandler {

	private Position originalPosition;
	private Position originalItemPosition;
	private Size originalSize;
	
	private static final long serialVersionUID = 7085318201596784835L;
	private Field field;
	private Position position;
	private Item owner;

	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.SE_RESIZE_CURSOR);

	public CircleBottomRightHandler(Item owner, Position position, Field field) {
		super(owner, position);
		this.field = field;
		this.owner = owner;
		this.position = position;
	}

	@Override
	public Cursor getCursor() {
		return RESIZE_CURSOR;
	}

	@Override
	public void startInteraction(Position p) {
		this.originalPosition = this.position;
		this.originalItemPosition = this.owner.getPosition();
		this.originalSize = this.owner.getSize();
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

		if(newS.getHeight() > 15)
		{
			this.position = newOrigin;
			java.awt.Rectangle r = getAWTRectangle(newS.getWidth(), newS.getHeight());

			//change Position and Size
			Position newP = new Position((int) r.getX(), (int) r.getY());
			this.position = p;
			super.setPosition(p);
			this.owner.setPosition(newP);
			this.owner.setSize(new Size((int) r.getWidth(), (int) r.getHeight()));
		}
	}

	@Override
	public void stopInteraction(Position p) {
		this.dragInteraction(p);
		
		if(!this.field.checkIfPositionFree(this.owner)) {
			this.position = this.originalPosition;
			super.setPosition(this.originalPosition);
			this.owner.setPosition(this.originalItemPosition);
			this.owner.setSize(this.originalSize);
		}
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
