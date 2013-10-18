package frontend.classes.items;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import frontend.interfaces.Item;
import frontend.interfaces.ItemChangedListener;
import frontend.interfaces.ItemHandler;


public class Line implements Item {
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandles = new ArrayList<ItemHandler>();
	private List<ItemChangedListener> listeners = new ArrayList<ItemChangedListener>();
	
	public Line(Position p) {
		this.position = p;
		this.size = new Size(4,4);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(this.position.getOriginX(), this.position.getOriginY(), 
				this.size.getWidth(), this.size.getHeight());
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
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
//		notifyShapeChangedListeners();

		//TODO
		/* set handles */
//		Coord c = new Coord(this.bb.getX0(), this.bb.getY0());
//		shapeHandles.get(0).setPosition(c);
//
//		Coord end = new Coord(this.bb.getX0() + this.bb.getWidth(), 
//				this.bb.getY0() + this.bb.getHeight());
//		shapeHandles.get(1).setPosition(end);
	}

	@Override
	public boolean contains(Position p) {
		return isCloseToLine(p, 2);
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandles;
	}

	@Override
	public void addItemChangedListener(ItemChangedListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public boolean removeItemChangedListener(ItemChangedListener listener) {
		return this.listeners.remove(listener);
	}
	
	//TODO
	private boolean isCloseToLine(Position p, int epsilon) {
		boolean rval = false;
//		Coord a = this.bb.getOrigin();
//		Coord b = new Coord(a.getOriginX() + this.bb.getWidth(), a.getOriginY()
//				+ this.bb.getHeight());
//		Vector oa = new Vector(a); // place vector of a
//		Vector ob = new Vector(b); // place vector of b
//		Vector oc = new Vector(c); // place vector of c
//		Vector ab = ob.minus(oa); // distance vector ab
//		Vector ac = oc.minus(oa); // distance vector ac
//		Vector ab_n = ab.getNormalVector(); // norm vector of ab
//		double D = ab.getDeterminantBy(ab_n);
//		double D_alpha = ac.getDeterminantBy(ab_n);
//		double D_beta = ab.getDeterminantBy(ac);
//		double alpha = D_alpha / D;
//		double distance = 0.0;
//		if (alpha < 0.0 || alpha > 1.0) {
//			rval = false;
//		} else {
//			double beta = D_beta / D;
//			distance = Math.abs(beta * ab_n.magnitude());
//			rval = distance <= epsilon;
//		}
		return rval;
	}

}
