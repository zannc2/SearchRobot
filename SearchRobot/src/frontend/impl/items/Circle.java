package frontend.impl.items;

import frontend.impl.items.handler.CircleBottomLeftHandler;
import frontend.impl.items.handler.CircleBottomRightHandler;
import frontend.impl.items.handler.CircleTopLeftHandler;
import frontend.impl.items.handler.CircleTopRightHandler;
import frontend.impl.view.Field;
import frontend.interfaces.ItemHandler;
import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for a circle Item and extends the {@link AbstractItem} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class Circle extends AbstractItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 472334084409780059L;
	
	private Position position;
	private Size size;
	private Field field;
	private List<ItemHandler> itemHandlers = new ArrayList<ItemHandler>();

	/**
	 * Constructor defines field, position of the circle and creates the handlers for the circle
	 * 
	 * @param p Position of the 
	 * @param field the field which contains the circle
	 */
	public Circle(Position p, Field field) {
		this.field = field;
		this.position = p;
		this.size = new Size(1, 1);

		// Add handlers
		this.itemHandlers.add(new CircleTopLeftHandler(this, p, this.field));
		Position topRight = new Position(this.position.getOriginX() + this.size.getWidth(), this.position.getOriginY());
		this.itemHandlers.add(new CircleTopRightHandler(this, topRight, this.field));
		Position bottomLeft = new Position(this.position.getOriginX(), this.position.getOriginY() + this.size.getHeight());
		this.itemHandlers.add(new CircleBottomLeftHandler(this, bottomLeft, this.field));
		Position bottomRight = new Position(this.position.getOriginX() + this.size.getWidth(),
				this.position.getOriginY() + this.size.getHeight());	
		this.itemHandlers.add(new CircleBottomRightHandler(this, bottomRight, this.field));
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Rectangle r = Size.getAWTRectangle(this.size, this.position);
		g2.fill(new Ellipse2D.Double(r.getX(), r.getY(),
				r.getWidth(), r.getHeight()));
	}

	
	@Override
	public void setPosition(Position position) {
		this.position = position;
		notifyItemChangedListeners();
		
		// set Handler topLeft
		this.itemHandlers.get(0).setPosition(position);
		this.itemHandlers.get(1).setPosition(new Position(position.getOriginX() + this.size.getWidth(), position.getOriginY()));
		this.itemHandlers.get(2).setPosition(new Position(position.getOriginX(), position.getOriginY() + this.size.getHeight()));
		this.itemHandlers.get(3).setPosition(new Position(position.getOriginX() + this.size.getWidth(), position.getOriginY() + this.size.getHeight()));
	}

	@Override
	public boolean contains(Position p) {
		Ellipse2D.Double ellipse = new Ellipse2D.Double(this.position.getOriginX(), this.position.getOriginY(),
				this.size.getWidth(), this.size.getHeight());
		boolean rt = ellipse.contains(new Point(p.getOriginX(), p.getOriginY()));
		return rt;

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
		notifyItemChangedListeners();
		// set Handlers
		Rectangle r = Size.getAWTRectangle(this.size, this.position);
		Position topLeft = new Position((int)r.getX(), (int)r.getY());
		this.itemHandlers.get(1).setPosition(topLeft);
		Position topRight = new Position((int)(r.getX() + r.getWidth()), (int)r.getY());
		this.itemHandlers.get(1).setPosition(topRight);
		Position bottomLeft = new Position((int)r.getX(), (int)(r.getY() + r.getHeight()));
		this.itemHandlers.get(2).setPosition(bottomLeft);;
		Position bottomRight = new Position((int)(r.getX()+r.getWidth()), (int)(r.getY()+r.getHeight()));	
		this.itemHandlers.get(3).setPosition(bottomRight);
	}

	@Override
	public Size getSize() {
		return this.size;
	}
}
