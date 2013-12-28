package frontend.impl.items.handler;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;

public class CircleBottomLeftHandler extends AbstractHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7472469873528205205L;
	
	private Position originalPosition;
	private Position originalItemPosition;
	private Size originalSize;
	private Position position;
	private Item owner;
	private Field field;

	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.SW_RESIZE_CURSOR);

	public CircleBottomLeftHandler(Item owner, Position position, Field field) {
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
		Position topRight = new Position(origP.getOriginX() + origS.getWidth(), origP.getOriginY());

		// calculate new Size
		int width = topRight.getOriginX() - p.getOriginX();
		int height = p.getOriginY() - topRight.getOriginY();

		Position newOrigin = null;
		Size newS = null;
		if(width > height) {
			newOrigin = new Position(topRight.getOriginX() - width, topRight.getOriginY());
			newS = new Size(width, width);
		}
		else {
			newOrigin = new Position(topRight.getOriginX() - height, topRight.getOriginY());	
			newS = new Size(height, height);
		}

		if(newS.getHeight() > 15)
		{
			this.position = newOrigin;
			java.awt.Rectangle r = Size.getAWTRectangle(newS, this.position);

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
			super.setPosition(originalPosition);
			this.owner.setPosition(this.originalItemPosition);
			this.owner.setSize(this.originalSize);
		}
	}
}
