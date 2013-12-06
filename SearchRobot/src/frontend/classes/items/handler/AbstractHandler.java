package frontend.classes.items.handler;

import helper.Position;
import helper.Size;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

public abstract class AbstractHandler implements ItemHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3242887632529170092L;

	/** The size of the quadratic handle. */
	public static final Size HANDLE_SIZE = new Size(16,16);
	
	/** The size of the pen. */
	public static final int PEN_SIZE = 1;

	
	private Position position;
	private Item owner;
	
	protected AbstractHandler(Item owner, Position position) {
		this.owner = owner;
		this.position = position;
	}
	
	@Override
	public Item getOwner() {
		return this.owner;
	}

	@Override
	public void setPosition(Position p) {
		this.position = p;
	}

	@Override
	public Position getPositioin() {
		return this.position;
	}

	@Override
	public Size getSize() {
		return HANDLE_SIZE;
	}

	@Override
	public boolean contains(Position p) {
		return new Rectangle(getX0(), getY0(), getWidth(), getHeight())
		.contains(p.getOriginX(), p.getOriginY());
	}
	
	private int getX0() {
		return this.position.getOriginX() - HANDLE_SIZE.getWidth() / 2;
	}

	private int getY0() {
		return this.position.getOriginY() - HANDLE_SIZE.getHeight() / 2;
	}

	private int getWidth() {
		return HANDLE_SIZE.getWidth();
	}

	private int getHeight() {
		return HANDLE_SIZE.getHeight();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke stroke = new BasicStroke(PEN_SIZE);
		g2.setStroke(stroke);
		g2.draw(new Rectangle(getX0(), getY0(), getWidth(), getHeight()));
	}

	@Override
	public void startInteraction(Position p) {
	}

	@Override
	public void dragInteraction(Position p) {
	}

	@Override
	public void stopInteraction(Position p) {
	}
	
	protected boolean checkIteractionPossible() {
		return false;
	}

}
