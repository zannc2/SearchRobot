package frontend.classes.items;

import frontend.classes.items.handler.CircleBottomLeftHandler;
import frontend.classes.items.handler.CircleBottomRightHandler;
import frontend.classes.items.handler.CircleTopLeftHandler;
import frontend.classes.items.handler.CircleTopRightHandler;
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

	private List<ItemHandler> itemHandlers = new ArrayList<ItemHandler>();

	public Circle(Position p) {
		this.position = p;
		this.size = new Size(1, 1);

		// Add handlers
		this.itemHandlers.add(new CircleTopLeftHandler(this, p));
		Position topRight = new Position(this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY());
		this.itemHandlers.add(new CircleTopRightHandler(this, topRight));
		Position bottomLeft = new Position(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.add(new CircleBottomLeftHandler(this, bottomLeft));
		Position bottomRight = new Position(this.position.getOriginX() + this.size.getWidth(),
				this.position.getOriginY() + this.size.getHeight());	
		this.itemHandlers.add(new CircleBottomRightHandler(this, bottomRight));
	}

	@Override
	public void draw(Graphics g) {
//		java.awt.Rectangle r = getAWTRectangle();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginY(),
				this.size.getWidth(), this.size.getHeight()));
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
		notifyItemChangedListeners();
		
		// set Handler topLeft
		this.itemHandlers.get(0).setPosition(position);
	}

	@Override
	public boolean contains(Position p) {
		return new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginX(),
				this.size.getWidth(), this.size.getHeight()).contains(p.getAWTPoint());

	}

	@Override
	public List<ItemHandler> getItemHandler() {
		return this.itemHandlers;	
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

		// set handles 
		this.itemHandlers.get(0).setPosition(this.position);
		Position topRight = new Position(this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY());
		this.itemHandlers.get(1).setPosition(topRight);;
		Position bottomLeft = new Position(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.get(2).setPosition(bottomLeft);;
		Position bottomRight = new Position(this.position.getOriginX() + this.size.getWidth(),
				this.position.getOriginY() + this.size.getHeight());	
		this.itemHandlers.get(3).setPosition(bottomRight);
		
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setSize(Size size) {
		
		this.size = size;
		// calculate positive Values
		java.awt.Rectangle r = getAWTRectangle();
		
		System.out.println("new Rectangle: " + r);
		
		// set new Size and Position with positive Values
		this.size = new Size((int) r.getWidth(), (int) r.getHeight());
		this.position = new Position((int) r.getX(), (int) r.getY());
		notifyItemChangedListeners();
		
		// set Handlers
		Position topRight = new Position(this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY());
		this.itemHandlers.get(1).setPosition(topRight);;
		Position bottomLeft = new Position(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.get(2).setPosition(bottomLeft);;
		Position bottomRight = new Position(this.position.getOriginX() + this.size.getWidth(),
				this.position.getOriginY() + this.size.getHeight());	
		this.itemHandlers.get(3).setPosition(bottomRight);
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
