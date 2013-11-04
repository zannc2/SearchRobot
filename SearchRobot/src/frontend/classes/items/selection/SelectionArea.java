package frontend.classes.items.selection;

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

import frontend.classes.items.AbstractItem;
import frontend.interfaces.ItemHandler;

public class SelectionArea extends AbstractItem {
	
	private Position position;
	private Size size;
	private Color penColor;
	
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
