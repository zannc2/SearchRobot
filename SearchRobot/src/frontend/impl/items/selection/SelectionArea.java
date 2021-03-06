package frontend.impl.items.selection;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.List;

import frontend.impl.items.AbstractItem;
import frontend.interfaces.ItemHandler;

/**
 * This is the implementation of the Selection Area. It extends the Abstract Item. 
 * The SelectionArea is used to draw a selection of items. 
 * @author zannc2 & gfels4
 *
 */
public class SelectionArea extends AbstractItem {

	private static final long serialVersionUID = 940976930726769873L;
	
	private Position position;
	private Size size;
	private Color penColor;
	
	/**
	 * Constructor which defines the Position of the SelectionArea.
	 * @param p Position of the SelectionArea
	 */
	public SelectionArea(Position p) {
		this.position = p;
		this.size = new Size(0,0);
		this.penColor =Color.BLUE;
	}

	@Override
	public void draw(Graphics g) {
		java.awt.Rectangle r = getAWTRectangle();
		Graphics2D g2 = (Graphics2D) g;
		Color current = g2.getColor();
		Stroke stroke = new BasicStroke(1);
		g2.setStroke(stroke);
		g2.setColor(this.penColor);
		g2.draw(r);
		g2.setColor(current);
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
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
	public void move(Vector delta) {
		//not needed
	}

	@Override
	public boolean contains(Position p) {
		return getAWTRectangle().contains(p.getAWTPoint());
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		//not needed
		return null;
	}
	
	private Rectangle getAWTRectangle() {
		int width = this.size.getWidth();
		int height = this.size.getHeight();
		Rectangle r = null;
		if (width >= 0) {
			if (height >= 0) {
				r = new Rectangle(this.position.getOriginX(), this.position
						.getOriginY(), width, height);
			} else {
				// width >= 0 && height < 0
				r = new Rectangle(this.position.getOriginX(), this.position
						.getOriginY()
						+ height, width, -height);
			}
		} else {
			if (height >= 0) {
				r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY(), -width, height);
			} else {
				// width < 0 && height < 0
				r = new Rectangle(this.position.getOriginX() + width, this.position
						.getOriginY()
						+ height, -width, -height);
			}
		}
		return r;
	}
}
