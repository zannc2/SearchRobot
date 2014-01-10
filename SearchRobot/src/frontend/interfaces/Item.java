package frontend.interfaces;

import helper.Position;
import helper.Size;
import helper.Vector;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;

/**
 * Item in the Field
 * @author zannc2 & gfells 4
 *
 */
public interface Item extends Serializable {
	/**
	 * Draws the Item in the Graphics
	 * @param g A Graphics context
	 */
	public abstract void draw(Graphics g);

	/**
	 * Set new Position of the Item
	 * @param position new Positin
	 */
	public abstract void setPosition(Position position);
	
	/**
	 * returns the Position of the Item
	 * 
	 * @return the position of the item
	 */
	public abstract Position getPosition();
	
	/**
	 * set the size of the Item
	 * @param size
	 */
	public abstract void setSize(Size size);
	
	/**
	 * returns the Size of the Item
	 * @return the size of the item
	 */
	public abstract Size getSize();
	
	/**
	 * Move the Item with delta
	 * @param delta moved delta
	 */
	public abstract void move(Vector delta);
	
	/**
	 * Tests weather the Position is contained in the Item.
	 * @param p Position
	 * @return if it is contained it returns true
	 */
	public boolean contains(Position p);
	
	/**
	 * returns a List of ItemHandler
	 * A ItemHandler is used to manipulate the Item. 
	 * @return List of ItemHandler
	 */
	public List<ItemHandler> getItemHandler();
	
	/**
	 * Add a ItemChangedListener to the Item. Whenever the Item changes, 
	 * he informs the ItemChangedListener
	 * @param listener
	 */
	public void addItemChangedListener(ItemChangedListener listener);

	/**
	 * Remove a ItemChangedListener
	 * @param listener
	 * @return true if removed successful
	 */
	public boolean removeItemChangedListener(ItemChangedListener listener);

}
