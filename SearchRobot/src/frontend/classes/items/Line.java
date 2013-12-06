package frontend.classes.items;

import frontend.classes.items.handler.LineEndHandler;
import frontend.classes.items.handler.LineOriginHandler;
import frontend.classes.view.Field;
import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;


public class Line extends AbstractItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5100543977628153129L;
	private Position position;
	private Size size;
	private Field field;
	
	private List<ItemHandler> itemHandlers = new ArrayList<ItemHandler>();
	
	public Line(Position p, Field f) {
		this.field = f;
		this.position = p;
		this.size = new Size(10, 10);
		
		/* Add handlers */
		this.itemHandlers.add(new LineOriginHandler(this, p, this.field));
		Position end = new Position(this.position.getOriginX() + this.size.getWidth(), 
				this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.add(new LineEndHandler(this, end, this.field));
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(10));
		g2.drawLine(this.position.getOriginX(), this.position.getOriginY(), 
				this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY() + this.size.getHeight());
		//System.out.println("X-Pos: " + this.position.getOriginX() + " YPos: " + this.position.getOriginY() + " height: " + this.getSize().getHeight() + "Width: " + this.getSize().getWidth());
//		System.out.println("draw Line size: " + this.size);
	}

	@Override
	public void move(Vector delta) {
		/* origin cordinates*/
		int x = this.position.getOriginX();
		int y = this.position.getOriginY();
		
		/* Move */
		x = x + delta.getXComponent();
		y = y + delta.getYComponent();
		
		this.position = new Position(x, y);

		//TODO
		/* set handles */
		this.itemHandlers.get(0).setPosition(this.position);

		Position end = new Position(this.position.getOriginX() + this.size.getWidth(), 
				this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.get(1).setPosition(end);
		
		notifyItemChangedListeners();
	}
	
	@Override
	public void setPosition(Position position) {
		this.position = position;

		/* set handles */
		itemHandlers.get(0).setPosition(position);
		Position end = new Position(this.position.getOriginX() + this.size.getWidth(), 
				this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.get(1).setPosition(end);

		notifyItemChangedListeners();
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setSize(Size size) {
		this.size = size;
		notifyItemChangedListeners();

		/* set handles */
		Position end = new Position(this.position.getOriginX() + this.size.getWidth(), 
				this.position.getOriginY() + this.size.getHeight());
		itemHandlers.get(1).setPosition(end);
	}

	@Override
	public Size getSize() {
		return this.size;
	}


	@Override
	public boolean contains(Position p) {
		return isCloseToLine(p, 5);
	}
	


	@Override
	public boolean contains(Position p, int epsilon) {
		return isCloseToLine(p, 5);
	}
	
	/**
	 * calculations if Position is in line
	 * @param p Position
	 * @param epsilon 
	 * @return
	 */
	private boolean isCloseToLine(Position p, int epsilon) {
		int p1 = (int)Math.sqrt(Math.pow(this.position.getOriginX()-p.getOriginX(), 2) + Math.pow(this.position.getOriginY()-p.getOriginY(), 2));
		int p2 = (int)Math.sqrt(Math.pow((this.size.getWidth() + this.position.getOriginX())-p.getOriginX(), 2) + Math.pow((this.size.getHeight() + this.position.getOriginY())-p.getOriginY(), 2));
		if(p1 <= 6 || p2 <= 6) return true;
		boolean rval = false;
		Position a = this.position;
		Position b = new Position(this.size.getWidth() + this.position.getOriginX(), 
				this.size.getHeight() + this.position.getOriginY());
		Vector oa = new Vector(a); // place vector of a
		Vector ob = new Vector(b); // place vector of b
		Vector oc = new Vector(p); // place vector of c
		Vector ab = ob.minus(oa); // distance vector ab
		Vector ac = oc.minus(oa); // distance vector ac
		Vector ab_n = ab.getNormalVector(); // norm vector of ab
		double D = ab.getDeterminantBy(ab_n);
		double D_alpha = ac.getDeterminantBy(ab_n);
		double D_beta = ab.getDeterminantBy(ac);
		double alpha = D_alpha / D;
		double distance = 0.0;
		if (alpha < 0.0 || alpha > 1.0) {
			rval = false;
		} else {
			double beta = D_beta / D;
			distance = Math.abs(beta * ab_n.magnitude());
			rval = distance <= epsilon;
		}
		return rval;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandlers;
	}

}
