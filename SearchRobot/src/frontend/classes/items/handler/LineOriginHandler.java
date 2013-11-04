package frontend.classes.items.handler;

import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class LineOriginHandler extends AbstractHandler {

	private Position position;
	private Item owner;
	
	public LineOriginHandler(Item owner, Position position) {
		super(owner, position);
		this.owner = owner;
		this.position = position;
	}

	@Override
	public void startInteraction(Position p) {
		System.out.println("start LineOriginHandler - start");
	}

	@Override
	public void dragInteraction(Position p) {
		System.out.println("start LineEndHandle - drag");
		/* get end point*/
		Position endPoint = new Position(this.position.getOriginX() + this.owner.getSize().getWidth(),
				this.position.getOriginY() + this.owner.getSize().getHeight());
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
