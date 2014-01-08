package frontend.impl.items.handler;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;

/**
 * This is the end handler of the line. 
 * It extends the AbstractHandler. 
 * @author zannc2 & gfells4
 *
 */
public class LineEndHandler extends AbstractHandler {
	
	private static final long serialVersionUID = 4240747856462608637L;
	
	private Position position;
	private Position originalPosition;
	private Position originalItemPosition;
	private Size originalSize;
	private Item owner;
	private Field field;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.S_RESIZE_CURSOR);

	/**
	 * Constructor which defines the field, owner and position of the handler.
	 * @param owner Item
	 * @param position
	 * @param field
	 */
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
