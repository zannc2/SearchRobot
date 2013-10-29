package frontend.interfaces;

import helper.Position;

public interface Tool {
	
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


}
