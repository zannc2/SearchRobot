package frontend.interfaces;

import helper.Position;
import helper.Size;

import java.awt.Cursor;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Handler for the Items
 * Handler are used to resize the Items 
 * @author zannc2 & gfells4
 *
 */
public interface ItemHandler extends Serializable{
	
	/**
	 * Gets the Handler's owner. 
	 * @return The Item which owns this Handler
	 */
	public Item getOwner();
	
	/**
	 * Sets a new Position of the Handler. 
	 * @param p new Position
	 */
	public void setPosition(Position p);
	
	/**
	 * Gets the Position of the Handler. 
	 * @return Position
	 */
	public Position getPositioin();
	
	/**
	 * Gets the Size of the Handler.
	 * @return Size
	 */
	public Size getSize();
	
	/**
	 * Draws this handle. 
	 * @param g The Graphics context.
	 */
	public void draw(Graphics g);
	
	/**
	 * Returns curser wicht should be displayed when the mouse is over the handle.
	 * @return The Cursor
	 */
	public abstract Cursor getCursor();
	
	/**
	 * Tests if the Position is contained in the Handler
	 * @param p Position
	 * @return True if is contained. 
	 */
	public boolean contains(Position p);
	 
	/**
	 * Start of an Interaction on the Handler
	 * @param p Position
	 */
	public void startInteraction(Position p);
	
	/**
	 * Drag Interaction on the Hanlder
	 * @param p Position
	 */
	public void dragInteraction(Position p);
	
	/**
	 * Stop Interaction on the Handler
	 * @param p Position
	 */
	public void stopInteraction(Position p);
	
	

}
