package frontend.impl.items.handler;

import frontend.impl.view.Field;
import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.Cursor;

public class LineOriginHandler extends AbstractHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2242302675505928594L;
	private Position position;
	private Position originalLinePosition;
	private Size originalSize;
	private Item owner;
	private Field field;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.N_RESIZE_CURSOR);
	
	public LineOriginHandler(Item owner, Position position, Field field) {
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
		this.originalLinePosition = this.owner.getPosition();
		this.originalSize = this.owner.getSize();
	}

	@Override
	public void dragInteraction(Position p) {
        System.out.println("dragIteragtion");
        /* get end point*/
		Position endPoint = new Position(
				this.owner.getPosition().getOriginX() + this.owner.getSize().getWidth(),
				this.owner.getPosition().getOriginY() + this.owner.getSize().getHeight());
		/* change Location*/
		this.position = p;
		super.setPosition(p);
		/* get new width and height */
		int width = endPoint.getOriginX() - this.position.getOriginX();
		int height = endPoint.getOriginY() - this.position.getOriginY();
		
		this.owner.setPosition(p);
		this.owner.setSize(new Size(width, height));
	}

	@Override
	public void stopInteraction(Position p) {
        System.out.println("stopIteraction");
        this.dragInteraction(p);
		
		if(!this.field.checkIfPositionFree(this.owner)) {
			this.owner.setPosition(this.originalLinePosition);
			this.owner.setSize(this.originalSize);
		}
	}
	
}
