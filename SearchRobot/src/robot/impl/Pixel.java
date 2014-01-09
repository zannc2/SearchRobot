package robot.impl;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import frontend.impl.items.AbstractItem;
import frontend.interfaces.ItemHandler;

/**
 * This is the class to represent a pixel(10x10px) item to show the user what the 
 * robot can see while the search. It can have different colors.
 * 
 * It extends the {@link AbstractItem} class
 * 
 * @author zannc2 & gfels4
 *
 */
public class Pixel extends AbstractItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Position position;
	private Size size;
	private Color color;

	/**
	 * Constructor defines its position, size and color and these values could not change
	 * 
	 * @param p
	 * @param c
	 */
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
		//not used 

	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setSize(Size size) {
		//not used
	}

	@Override
	public Size getSize() {
		return this.size;
	}

	@Override
	public void move(Vector delta) {
		//not used
	}

	@Override
	public boolean contains(Position p) {
		//not used
		return false;
	}

	@Override
	public List<ItemHandler> getItemHandler() {
		//not used
		return null;
	}

}
