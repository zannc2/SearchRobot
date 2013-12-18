package frontend.impl.items.handler;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;

public class LineEndHandler extends AbstractHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7023598776530603132L;
	private Position position;
	private Position originalPosition;
	private Position originalItemPosition;
	private Size originalSize;
	private Item owner;
	private Field field;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.S_RESIZE_CURSOR);

	public LineEndHandler(Item owner, Position position, Field field) {
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
		
		this.position = p;
		super.setPosition(p);
		int originX = this.owner.getPosition().getOriginX();
		int originY = this.owner.getPosition().getOriginY();
		
		int newX = this.position.getOriginX();
		int newY = this.position.getOriginY();
		int newWidth = newX - originX;
		int newHeight = newY - originY;
		this.owner.setSize(new Size(newWidth, newHeight));
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

}
