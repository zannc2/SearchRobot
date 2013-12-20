package frontend.interfaces;

import helper.Position;

import java.io.Serializable;

/**
 * The tool defines the methods used to create items. 
 * @author ca-za
 *
 */
public interface Tool extends Serializable {
	
	/**
	 * Handles mouse down events in the drawing view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseDown(Position p);


	/**
	 * Handles mouse-drag events in the view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseDrag(Position p);

	/**
	 * Handles mouse up in the view.
	 * 
	 * @param p Position of the mouse
	 */
	public void mouseUp(Position p);
	
	/**
	 * 
	 * @param p
	 */
	public void mouseOver(Position p);

}
