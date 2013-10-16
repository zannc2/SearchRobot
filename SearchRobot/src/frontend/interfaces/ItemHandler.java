package frontend.interfaces;

import helper.Position;
import helper.Size;

import java.awt.Cursor;
import java.awt.Graphics;

import jdt.framework.Coord;
import jdt.framework.KeyModifier;
import jdt.framework.View;

public interface ItemHandler {
	
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
	public Cursor getCursor();
	
	/*
	 * Missing interactions
	 * public boolean contains(Coord c);
	 * public void startInteraction(Coord c, KeyModifier k, View v);
	 * public void dragInteraction(Coord c, KeyModifier k, View v);
	 * public void stopInteraction(Coord c, KeyModifier k, View v);
	 * 
	 */

}
