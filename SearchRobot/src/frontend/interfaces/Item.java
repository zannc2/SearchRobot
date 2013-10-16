package frontend.interfaces;

import java.awt.Graphics;
import java.util.Vector;

public interface Item {
	
	/**
	 * Draws an implementation of this interface, i.e., a real shape, into the
	 * given Graphics context.
	 * 
	 * @param g
	 *            A Graphics context.
	 */
	public abstract void draw(Graphics g);

	/**
	 * Returns the bounding box of the shape.
	 * @return 
	 * 
	 * @return The bounding box.
	 */
	public abstract void setPosition(Position position);

	/**
	 * Sets the bounding box of the shape. The shape has to adjust its size and
	 * position when this method is called, and registered shape listeners have
	 * to be notified.
	 * 
	 * @param r
	 *            The new size of the bounding box.
	 */
	public void setBoundingBox(BoundingBox r);

	/**
	 * Moves an implementation of this interface along the vector (deltaX,
	 * deltaY).
	 * 
	 * @param delta
	 *            The vector along which the shape is moved.
	 */
	public abstract void move(Vector delta);


}
