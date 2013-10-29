package frontend.classes.items;

import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class Line extends AbstractItem {
	private Position position;
	private Size size;
	
	private List<ItemHandler> itemHandler = new ArrayList<ItemHandler>();
	
	public Line(Position p) {
		this.position = p;
		this.size = new Size(10, 10);
		
		//TODO create hanlder
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(this.position.getOriginX(), this.position.getOriginY(), 
				this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY() + this.size.getHeight());
		//System.out.println("X-Pos: " + this.position.getOriginX() + " YPos: " + this.position.getOriginY() + " height: " + this.getSize().getHeight() + "Width: " + this.getSize().getWidth());
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
		
		notifyItemChangedListeners();

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
	public void setPosition(Position position) {
		this.position = position;
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
	}

	@Override
	public Size getSize() {
		return this.size;
	}


	@Override
	public boolean contains(Position p) {
		return isCloseToLine(p, 2);
	}
	
	/**
	 * calculations if Position is in line
	 * @param p Position
	 * @param epsilon 
	 * @return
	 */
	private boolean isCloseToLine(Position p, int epsilon) {
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
		return this.itemHandler;
	}

}
