package frontend.classes.items.handler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import frontend.interfaces.Item;
import helper.Position;
import helper.Size;

public class LineEndHandler extends AbstractHandler {

	private Position position;
	private Item owner;
	
	private static final Cursor RESIZE_CURSOR = new Cursor(Cursor.S_RESIZE_CURSOR);

	public LineEndHandler(Item owner, Position position) {
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
		System.out.println("end LineHandle - start");
	}

	@Override
	public void dragInteraction(Position p) {
		System.out.println("end LineHandle - drag");

		this.position = p;
		super.setPosition(p);
		int originX = this.owner.getPosition().getOriginX();
		int originY = this.owner.getPosition().getOriginY();
		
		int newX = p.getOriginX();
		int newY = p.getOriginY();
		int newWidth = newX - originX;
		int newHeight = newY - originY;
		this.owner.setSize(new Size(newWidth, newHeight));
	}

	@Override
	public void stopInteraction(Position p) {
		System.out.println("end LineHandle - stop");
		this.dragInteraction(p);
	}

}
