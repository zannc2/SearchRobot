package robot.classes;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import frontend.classes.items.AbstractItem;
import frontend.interfaces.ItemHandler;

public class Pixel extends AbstractItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Position position;
	private Size size;
	private Color color;

	public Pixel(Position p, Color c) {
		this.position = p;
		this.size = new Size(10, 10);
		this.color = c;
	}
	
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Color current = g2.getColor();
		g2.setColor(this.color);
		g2.fillRect(this.position.getOriginX(), this.position.getOriginY(),
				this.size.getWidth(), this.size.getHeight());
		g2.setColor(current);
	}

	@Override
	public void setPosition(Position position) {
		// TODO Auto-generated method stub

	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSize(Size size) {
		// TODO Auto-generated method stub

	}

	@Override
	public Size getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Vector delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Position p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Position p, int epsilon) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
