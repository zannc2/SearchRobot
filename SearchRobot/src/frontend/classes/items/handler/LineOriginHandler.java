package frontend.classes.items.handler;

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
	private Item owner;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.N_RESIZE_CURSOR);
	
	public LineOriginHandler(Item owner, Position position) {
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
		System.out.println("start LineOriginHandler - start");
	}

	@Override
	public void dragInteraction(Position p) {
		System.out.println("start LineEndHandle - drag");
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
		System.out.println("start LineEndHandle - drag");
		this.dragInteraction(p);
	}
	
}
