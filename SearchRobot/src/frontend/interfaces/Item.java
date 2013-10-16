package frontend.interfaces;

import helper.Position;

import java.awt.Graphics;
import java.util.List;
import java.util.Vector;

public interface Item {
	
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
	 * @return
	 */
	public boolean removeItemChangedListener(ItemChangedListener listener);

}
