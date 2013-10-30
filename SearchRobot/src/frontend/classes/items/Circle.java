package frontend.classes.items;

import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;


public class Circle extends AbstractItem {

	private Position position;
	private Size size;

	private List<ItemHandler> itemHandler = new ArrayList<ItemHandler>();

	public Circle(Position p) {
		this.position = p;
		this.size = new Size(1, 1);
		//TODO greate Handler
	}

	@Override
	public void draw(Graphics g) {
		java.awt.Rectangle r = getAWTRectangle();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new Ellipse2D.Double(r.x, r.y, r.width, r
				.height));
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
		notifyItemChangedListeners();
	}

	@Override
	public boolean contains(Position p) {
		return new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginX(),
				this.size.getWidth(), this.size.getHeight()).contains(p.getAWTPoint());

	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandler;	
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

	private Rectangle getAWTRectangle() {
		Rectangle r = null;
		int width = size.getWidth();
		int height = size.getHeight();
		if (width >= 0) {
			if (height >= 0) {
				
				if(width > height)
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY(), width, width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY(), height, height);
				}
				
			} else {
				// width >= 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY() - width, width, width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX(), this.position
							.getOriginY() + height, -height, -height);
				}
			}
		} else {
			if (height >= 0) {
				if(Math.abs(width) > Math.abs(height))
				{
					r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY(), -width, -width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX() - height, this.position
							.getOriginY(), height, height);
				}
			} else {
				// width < 0 && height < 0
				if(Math.abs(width) > Math.abs(height))
				{
				r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY()
						+ width, -width, -width);
				}
				else
				{
					r = new Rectangle(this.position.getOriginX() + height, this.position
							.getOriginY()
							+ height, -height, -height);
				}
			}
		}
		return r;
	}

}
